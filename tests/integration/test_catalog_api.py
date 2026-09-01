"""Integration tests that exercise the generated Python catalog client against a
live Micronaut app instance. These are what catch a breaking API change: if the
server changes a field/endpoint/type without the client being regenerated to
match, one of these calls fails.
"""
import uuid

import pytest
from catalog_client.api.datasets_api import DatasetsApi
from catalog_client.exceptions import NotFoundException, UnauthorizedException
from catalog_client.models.dataset_dto import DatasetDTO
from catalog_client.models.dataset_sensitivity import DatasetSensitivity
from catalog_client.models.field_dto import FieldDTO


def _new_dataset(name: str) -> DatasetDTO:
    return DatasetDTO(
        name=name,
        owner_team="team-integration-tests",
        tags=["test"],
        sensitivity=DatasetSensitivity.INTERNAL,
        retention_days="30",
        refresh_interval_hours=24,
        schema_fields=[FieldDTO(name="id", type="STRING")],
        pii_fields=[],
    )


def test_list_datasets_includes_seed_data(api: DatasetsApi):
    datasets = api.list_datasets()
    assert len(datasets) >= 3
    assert any(d.name == "orders_daily" for d in datasets)


def test_create_get_update_delete_roundtrip(api: DatasetsApi):
    name = f"pytest_{uuid.uuid4().hex[:8]}"
    created = api.create_dataset(_new_dataset(name))
    assert created.id is not None
    assert created.name == name

    fetched = api.get_dataset(created.id)
    assert fetched.name == name

    updated_payload = _new_dataset(name)
    updated_payload.owner_team = "team-updated"
    updated = api.update_dataset(created.id, updated_payload)
    assert updated.owner_team == "team-updated"

    api.delete_dataset(created.id)

    with pytest.raises(NotFoundException):
        api.get_dataset(created.id)


def test_create_without_auth_is_rejected(unauthenticated_api: DatasetsApi):
    with pytest.raises(UnauthorizedException):
        unauthenticated_api.create_dataset(_new_dataset("should_be_rejected"))
