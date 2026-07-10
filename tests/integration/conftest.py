import os

import pytest
from catalog_client import ApiClient, Configuration
from catalog_client.api.datasets_api import DatasetsApi
from catalog_client.auth import authenticated_client


@pytest.fixture
def base_url() -> str:
    return os.environ.get("CATALOG_API_BASE_URL", "http://localhost:8080")


@pytest.fixture
def api(base_url):
    return DatasetsApi(authenticated_client(base_url=base_url))


@pytest.fixture
def unauthenticated_api(base_url):
    return DatasetsApi(ApiClient(Configuration(host=base_url)))
