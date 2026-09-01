---
theme: seriph
background: /images/api-cover-bg.svg
title: Stop Writing API Clients — Let the Spec Do It
titleTemplate: '%s'
info: |
  ## Stop Writing API Clients — Let the Spec Do It
  Demo talk for JavaZone 2026 — OpenAPI + openapi-generator keeping API clients honest.
class: text-center
drawings:
  persist: false
transition: slide-left
mdc: true
fonts:
  sans: 'Inter'
  mono: 'Fira Code'
---

# Stop Writing API Clients

### Let the Spec Do It

<br>

Javazone 2026 · Lightning Talk

<!--
Hi! My name is Rupinder. Working in Statistisk Sentralbyrå. This lightening tak is about how developers can generate API-clients automatically while they build API.

Hook: "Every team has that one API client someone wrote 18 months ago
that nobody remembers to update." May be some of us has experience this. 

A pipeline that makes a breaking API change fail CI instead of silently breaking a consumer.
-->

---
layout: default
class: 'bg-neutral-950'
---

<div class="flex justify-between px-16 mb-2">

<div class="relative rounded-xl px-4 py-2 bg-neutral-900 border border-neutral-700 text-neutral-200 text-center text-sm ml-8">
"got any data<br/>for me?"
<div class="absolute left-6 -bottom-1.5 w-3 h-3 bg-neutral-900 border-r border-b border-neutral-700 rotate-45"></div>
</div>

<div class="relative rounded-xl px-4 py-2 bg-neutral-900 border border-neutral-700 text-neutral-200 text-center text-sm mr-8">
"order up:<br/>one JSON!"
<div class="absolute right-6 -bottom-1.5 w-3 h-3 bg-neutral-900 border-r border-b border-neutral-700 rotate-45"></div>
</div>

</div>

<div class="flex items-center justify-center gap-2 mt-6">

<div class="rounded-2xl p-5 bg-blue-950 border border-blue-800 w-44 h-40 flex flex-col items-center justify-center gap-2 text-center">
<svg class="w-9 h-9 text-blue-300" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="2" y="4" width="20" height="14" rx="2"/><line x1="8" y1="21" x2="16" y2="21"/><line x1="12" y1="17" x2="12" y2="21"/></svg>
<div class="text-blue-100 font-bold">Consumer</div>
<div class="text-blue-300/70 text-xs">your code —<br/>app, script, service</div>
</div>

<div class="flex flex-col items-center gap-1 w-36">
<div class="text-neutral-300 text-xs text-center">"I'd like some data"</div>
<svg class="w-36 h-20" viewBox="0 0 144 80" xmlns="http://www.w3.org/2000/svg">
<defs>
<marker id="arrow-req-1" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#f59e0b" stroke-width="2"/></marker>
<marker id="arrow-res-1" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#2dd4bf" stroke-width="2"/></marker>
</defs>
<path d="M4,40 Q72,4 140,40" fill="none" stroke="#f59e0b" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-req-1)" class="flow-line"/>
<path d="M140,44 Q72,76 4,44" fill="none" stroke="#2dd4bf" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-res-1)" class="flow-line"/>
</svg>
<div class="text-neutral-300 text-xs text-center">"brings it back"</div>
</div>

<div class="flex flex-col items-center gap-2 w-32 text-center">
<div class="w-16 h-16 rounded-2xl bg-amber-950 border border-amber-800 flex items-center justify-center text-amber-300">
<svg class="w-8 h-8" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="7" r="4"/><path d="M5.5 21a6.5 6.5 0 0 1 13 0"/></svg>
</div>
<div class="text-amber-200 font-bold text-sm">the API Client<br/><span class="font-normal text-amber-300/70">(the waiter)</span></div>
</div>

<div class="flex flex-col items-center gap-1 w-36">
<div class="text-neutral-300 text-xs text-center">"passes it on"</div>
<svg class="w-36 h-20" viewBox="0 0 144 80" xmlns="http://www.w3.org/2000/svg">
<defs>
<marker id="arrow-req-2" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#f59e0b" stroke-width="2"/></marker>
<marker id="arrow-res-2" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#2dd4bf" stroke-width="2"/></marker>
</defs>
<path d="M4,40 Q72,4 140,40" fill="none" stroke="#f59e0b" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-req-2)" class="flow-line"/>
<path d="M140,44 Q72,76 4,44" fill="none" stroke="#2dd4bf" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-res-2)" class="flow-line"/>
</svg>
<div class="text-neutral-300 text-xs text-center">"here's your JSON"</div>
</div>

<div class="rounded-2xl p-5 bg-emerald-950 border border-emerald-800 w-44 h-40 flex flex-col items-center justify-center gap-2 text-center">
<svg class="w-9 h-9 text-emerald-300" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="8" cy="6" r="1.5"/><circle cx="16" cy="6" r="1.5"/><rect x="4" y="10" width="16" height="10" rx="2"/></svg>
<div class="text-emerald-100 font-bold">API</div>
<div class="text-emerald-300/70 text-xs">the kitchen —<br/>does the real work</div>
</div>

</div>

<div class="text-center mt-8">
<span class="inline-block px-3 py-1 rounded bg-neutral-800 text-neutral-300 text-sm font-medium">Consumer, API client, and API — shown as a restaurant</span>
</div>

<div class="text-center mt-3 text-neutral-400 text-sm max-w-2xl mx-auto">
the consumer never talks to the API directly —<br/>everything goes through the API client, who knows the menu (endpoints) and the rules (protocol)
</div>

<style scoped>
.flow-line {
  animation: dash-flow-rest 1s linear infinite;
}
@keyframes dash-flow-rest {
  to { stroke-dashoffset: -12; }
}
</style>

<!--
The gag, and this time it's the actual subject of the talk, not just
a generic "how APIs work" analogy: the consumer (your code) never
talks to the API directly. It orders through the API client (the
waiter), who knows the menu (available endpoints) and the house rules
(the protocol — REST, GraphQL, whatever). The API (the kitchen) does
the actual work and hands the finished dish back to the client, who
carries it to the consumer.

Where it stops being cute and starts being useful: that middle layer
is the whole point, and it's exactly what the rest of this talk is
about. The consumer doesn't need to know how the API is organized,
what's in its database, or how the response gets computed — it just
needs to know how to place an order and what shape the food (data)
will arrive in. Change the API's internals and nothing at the table
has to change, as long as the client's still taking the same orders —
and that "waiter" is a real thing you're about to watch get generated,
not hand-written, for the rest of this talk.
-->

---
layout: default
class: 'bg-neutral-950'
---

<div class="grid grid-cols-2 gap-6">

<div class="rounded-2xl p-5 bg-neutral-900 border border-neutral-800">

<div class="text-neutral-300 font-medium mb-3">Direct request</div>

<div class="flex items-center gap-3 mb-4 text-neutral-500">
<svg class="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="16 18 22 12 16 6"/><polyline points="8 6 2 12 8 18"/></svg>
<span>→</span>
<svg class="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="2" y1="12" x2="22" y2="12"/><path d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"/></svg>
<span>→</span>
<svg class="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><ellipse cx="12" cy="5" rx="9" ry="3"/><path d="M3 5v6c0 1.7 4 3 9 3s9-1.3 9-3V5"/><path d="M3 11v6c0 1.7 4 3 9 3s9-1.3 9-3v-6"/></svg>
</div>

```ts
const res = await fetch(
  `${baseUrl}/datasets/${id}`,
  { headers: { Authorization: `Bearer ${token}` } }
);
if (!res.ok) throw new Error(res.status);
const data = await res.json();
const dataset: Dataset = {
  id: data.id,
  name: data.name,
  owner: data.owner,
  tags: data.tags,
};
```



</div>

<div class="rounded-2xl p-5 bg-emerald-950 border border-emerald-900">

<div class="text-emerald-400 font-medium mb-3">Via API client</div>

<div class="flex items-center gap-3 mb-4 text-emerald-500">
<svg class="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="16 18 22 12 16 6"/><polyline points="8 6 2 12 8 18"/></svg>
<span>→</span>
<svg class="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg>
<span>→</span>
<svg class="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><ellipse cx="12" cy="5" rx="9" ry="3"/><path d="M3 5v6c0 1.7 4 3 9 3s9-1.3 9-3V5"/><path d="M3 11v6c0 1.7 4 3 9 3s9-1.3 9-3v-6"/></svg>
</div>

```ts
const dataset = await datasetsApi.getDataset(id);
```

</div>

</div>

<style scoped>
pre {
  background-color: #18181b !important;
  border: 1px solid #27272a;
}
pre code, pre code span {
  color: #e4e4e7 !important;
}
</style>

<!--
Left: a hand-rolled fetch —
build the URL, attach the bearer token, check the status, parse JSON,
manually copy fields into a typed shape. 

Right: one line, because the client already knows the URL, the auth header, and the
shape. 

That one line is only correct as long as the
client still matches what the API actually returns — which is exactly hand-written clients can't guarantee for long.
-->

---
layout: default
class: 'bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-6" style="color: #fff;">One API, three clients</h1>

<div class="flex flex-col items-center">

<div class="flex flex-col items-center text-neutral-300">
<svg class="w-9 h-9" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><ellipse cx="12" cy="5" rx="9" ry="3"/><path d="M3 5v6c0 1.7 4 3 9 3s9-1.3 9-3V5"/><path d="M3 11v6c0 1.7 4 3 9 3s9-1.3 9-3v-6"/></svg>
<div class="mt-2">API</div>
</div>

<svg class="w-full max-w-2xl h-9" viewBox="0 0 900 60" fill="none">
<path d="M450 0 V20 M150 20 H750 M150 20 V60 M450 20 V60 M750 20 V60" stroke="#3f3f46" stroke-width="2"/>
</svg>

<div class="grid grid-cols-3 gap-6 w-full max-w-2xl">

<div class="rounded-2xl p-6 bg-neutral-900 flex flex-col items-center text-center">
<svg class="w-10 h-10" viewBox="0 0 24 24" fill="#3776AB">
<path d="M14.25.18l.9.2.73.26.59.3.45.32.34.34.25.34.16.33.1.3.04.26.02.2-.01.13V8.5l-.05.63-.13.55-.21.46-.26.38-.3.31-.33.25-.35.19-.35.14-.33.1-.3.07-.26.04-.21.02H8.77l-.69.05-.59.14-.5.22-.41.27-.33.32-.27.35-.2.36-.15.37-.1.35-.07.32-.04.27-.02.21v3.06H3.17l-.21-.03-.28-.07-.32-.12-.35-.18-.36-.26-.36-.36-.35-.46-.32-.59-.28-.73-.21-.88-.14-1.05-.05-1.23.06-1.22.16-1.04.24-.87.32-.71.36-.57.4-.44.42-.33.42-.24.4-.16.36-.1.32-.05.24-.01h.16l.06.01h8.16v-.83H6.18l-.01-2.75-.02-.37.05-.34.11-.31.17-.28.25-.26.31-.23.38-.2.44-.18.51-.15.58-.12.64-.1.71-.06.77-.04.84-.02 1.27.05zm-6.3 1.98l-.23.33-.08.41.08.41.23.34.33.22.41.09.41-.09.33-.22.23-.34.08-.41-.08-.41-.23-.33-.33-.22-.41-.09-.41.09zm13.09 3.95l.28.06.32.12.35.18.36.27.36.35.35.47.32.59.28.73.21.88.14 1.04.05 1.23-.06 1.23-.16 1.04-.24.86-.32.71-.36.57-.4.45-.42.33-.42.24-.4.16-.36.09-.32.05-.24.02-.16-.01h-8.22v.82h5.84l.01 2.76.02.36-.05.34-.11.31-.17.29-.25.25-.31.24-.38.2-.44.17-.51.15-.58.13-.64.09-.71.07-.77.04-.84.01-1.27-.04-1.07-.14-.9-.2-.73-.25-.59-.3-.45-.33-.34-.34-.25-.34-.16-.33-.1-.3-.04-.25-.02-.2.01-.13v-5.34l.05-.64.13-.54.21-.46.26-.38.3-.32.33-.24.35-.2.35-.14.33-.1.3-.06.26-.04.21-.02.13-.01h5.84l.69-.05.59-.14.5-.21.41-.28.33-.32.27-.35.2-.36.15-.36.1-.35.07-.32.04-.28.02-.21V6.07h2.09l.14.01zm-6.47 14.25l-.23.33-.08.41.08.41.23.33.33.23.41.08.41-.08.33-.23.23-.33.08-.41-.08-.41-.23-.33-.33-.23-.41-.08-.41.08z"/>
</svg>
<div class="mt-3 text-neutral-300 text-sm">Python client</div>
</div>

<div class="rounded-2xl p-6 bg-neutral-900 flex flex-col items-center text-center">
<svg class="w-10 h-10" viewBox="0 0 24 24">
<rect x="2" y="2" width="20" height="20" rx="3" fill="#fff"/>
<path fill="#3178C6" d="M1.125 0C.502 0 0 .502 0 1.125v21.75C0 23.498.502 24 1.125 24h21.75c.623 0 1.125-.502 1.125-1.125V1.125C24 .502 23.498 0 22.875 0zm17.363 9.75c.612 0 1.154.037 1.627.111a6.38 6.38 0 0 1 1.306.34v2.458a3.95 3.95 0 0 0-.643-.361 5.093 5.093 0 0 0-.717-.26 5.453 5.453 0 0 0-1.426-.2c-.3 0-.573.028-.819.086a2.1 2.1 0 0 0-.623.242c-.17.104-.3.229-.393.374a.888.888 0 0 0-.14.49c0 .196.053.373.156.529.104.156.252.304.443.444s.423.276.696.41c.273.135.582.274.926.416.47.197.892.407 1.266.628.374.222.695.473.963.753.268.279.472.598.614.957.142.359.214.776.214 1.253 0 .657-.125 1.21-.373 1.656a3.033 3.033 0 0 1-1.012 1.085 4.38 4.38 0 0 1-1.487.596c-.566.12-1.163.18-1.79.18a9.916 9.916 0 0 1-1.84-.164 5.544 5.544 0 0 1-1.512-.493v-2.63a5.033 5.033 0 0 0 3.237 1.2c.333 0 .624-.03.872-.09.249-.06.456-.144.623-.25.166-.108.29-.234.373-.38a1.023 1.023 0 0 0-.074-1.089 2.12 2.12 0 0 0-.537-.5 5.597 5.597 0 0 0-.807-.444 27.72 27.72 0 0 0-1.007-.436c-.918-.383-1.602-.852-2.053-1.405-.45-.553-.676-1.222-.676-2.005 0-.614.123-1.141.369-1.582.246-.441.58-.804 1.004-1.089a4.494 4.494 0 0 1 1.47-.629 7.536 7.536 0 0 1 1.77-.201zm-15.113.188h9.563v2.166H9.506v9.646H6.789v-9.646H3.375z"/>
</svg>
<div class="mt-3 text-neutral-300 text-sm">TypeScript client</div>
</div>

<div class="rounded-2xl p-6 bg-neutral-900 flex flex-col items-center text-center">
<svg class="w-12 h-10" viewBox="0 0 24 24" fill="#00ADD8">
<path d="M1.811 10.231c-.047 0-.058-.023-.035-.059l.246-.315c.023-.035.081-.058.128-.058h4.172c.046 0 .058.035.035.07l-.199.303c-.023.036-.082.07-.117.07zM.047 11.306c-.047 0-.059-.023-.035-.058l.245-.316c.023-.035.082-.058.129-.058h5.328c.047 0 .07.035.058.07l-.093.28c-.012.047-.058.07-.105.07zm2.828 1.075c-.047 0-.059-.035-.035-.07l.163-.292c.023-.035.07-.07.117-.07h2.337c.047 0 .07.035.07.082l-.023.28c0 .047-.047.082-.082.082zm12.129-2.36c-.736.187-1.239.327-1.963.514-.176.046-.187.058-.34-.117-.174-.199-.303-.327-.548-.444-.737-.362-1.45-.257-2.115.175-.795.514-1.204 1.274-1.192 2.22.011.935.654 1.706 1.577 1.835.795.105 1.46-.175 1.987-.77.105-.13.198-.27.315-.434H10.47c-.245 0-.304-.152-.222-.35.152-.362.432-.97.596-1.274a.315.315 0 01.292-.187h4.253c-.023.316-.023.631-.07.947a4.983 4.983 0 01-.958 2.29c-.841 1.11-1.94 1.8-3.33 1.986-1.145.152-2.209-.07-3.143-.77-.865-.655-1.356-1.52-1.484-2.595-.152-1.274.222-2.419.993-3.424.83-1.086 1.928-1.776 3.272-2.02 1.098-.2 2.15-.07 3.096.571.62.41 1.063.97 1.356 1.648.07.105.023.164-.117.2m3.868 6.461c-1.064-.024-2.034-.328-2.852-1.029a3.665 3.665 0 01-1.262-2.255c-.21-1.32.152-2.489.947-3.529.853-1.122 1.881-1.706 3.272-1.95 1.192-.21 2.314-.095 3.33.595.923.63 1.496 1.484 1.648 2.605.198 1.578-.257 2.863-1.344 3.962-.771.783-1.718 1.273-2.805 1.495-.315.06-.63.07-.934.106zm2.78-4.72c-.011-.153-.011-.27-.034-.387-.21-1.157-1.274-1.81-2.384-1.554-1.087.245-1.788.935-2.045 2.033-.21.912.234 1.835 1.075 2.21.643.28 1.285.244 1.905-.07.923-.48 1.425-1.228 1.484-2.233z"/>
</svg>
<div class="mt-3 text-neutral-300 text-sm">Go client</div>
</div>

</div>

</div>


<!--
That one-line client from the previous slide comes from somewhere —
this is what sits behind it. One API, but three teams each hand-wrote
their own client to talk to it: Python, TypeScript, Go.

Click 1, "Same endpoints, three different languages": same HTTP
contract, reimplemented three times by hand.

Click 2, "Each hand-written and maintained separately": no shared
code between them — a fix or workaround in one doesn't reach the
other two.

Click 3, "A change to the API means three separate fixes": and
that's only if whoever owns each client actually remembers to make
it. Keep that in mind — the rest of the talk is about closing that
gap.
-->

---
layout: default
class: 'bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-8" style="color: #fff;">What hand-written clients cost you</h1>

<div class="grid grid-cols-3 gap-5 mt-6">

<v-click>

<div class="rounded-2xl p-6 bg-gradient-to-b from-orange-950 to-neutral-900 border border-orange-900 text-center transition-all duration-500 h-56 flex flex-col items-center justify-center">
<div class="text-5xl mb-3">🔥</div>
<div class="meme-caption text-2xl">this is fine</div>
<div class="text-neutral-400 text-xs mt-3">Breaking change hit prod.<br/>Nobody told the client.</div>
</div>

</v-click>

<v-click>

<div class="rounded-2xl bg-neutral-900 border border-neutral-800 text-center transition-all duration-500 h-56 flex flex-col overflow-hidden">
<div class="flex-1 flex items-center gap-3 px-4 border-b border-neutral-800 opacity-40">
<span class="text-2xl">✗</span>
<span class="text-neutral-300 text-sm text-left">Reading the API changelog</span>
</div>
<div class="flex-1 flex items-center gap-3 px-4 bg-emerald-950/40">
<span class="text-2xl">✓</span>
<span class="text-neutral-100 text-sm text-left font-medium">Finding out from a customer ticket</span>
</div>
</div>

</v-click>

<v-click>

<div class="rounded-2xl p-6 bg-neutral-900 border border-neutral-800 text-center transition-all duration-500 h-56 flex flex-col items-center justify-center">
<div class="text-5xl mb-3">🔁</div>
<div class="meme-caption text-xl">same bug,<br/>different day</div>
<div class="text-neutral-400 text-xs mt-3">Update the client. By hand.<br/>Every single time.</div>
</div>

</v-click>

</div>

<style scoped>
.slidev-vclick-target {
  transform: translateY(12px);
}
.slidev-vclick-target:not(.slidev-vclick-hidden) {
  transform: translateY(0);
}
.meme-caption {
  font-family: Impact, 'Arial Black', sans-serif;
  font-weight: 900;
  text-transform: uppercase;
  color: #fff;
  line-height: 1.15;
  letter-spacing: 0.01em;
  text-shadow:
    -2px -2px 0 #000, 2px -2px 0 #000,
    -2px 2px 0 #000, 2px 2px 0 #000,
    0 3px 6px rgba(0,0,0,0.5);
}
</style>

<!--
Three cards, one click per beat, almost no reading required — let each
land as a beat of humor before naming the real cost out loud. Don't
read the on-screen captions verbatim, say the fuller version below.

Click 1 — on screen: a 🔥 emoji over a bold black-outlined caption
"THIS IS FINE" (the dog-sitting-in-a-burning-room meme, recreated
through caption styling only, no dog image), with a small line
underneath: "Breaking change hit prod. Nobody told the client." The
meaning: a breaking change reaches production and the first anyone
hears about it is an alert, not a heads-up — because nothing checked
whether the client still matched the API before it shipped.

Click 2 — on screen: the Drake two-panel format, recreated with ✗/✓
instead of the actual photo. Top panel, dimmed: ✗ "Reading the API
changelog" (the rejected, sensible option). Bottom panel, highlighted
green: ✓ "Finding out from a customer ticket" (the "preferred",
absurd option). The meaning: hand-written clients get updated at the
speed of whichever team last had spare cycles, not at the speed of
the API — so in practice, "finding out when it breaks" is what
actually happens by default, not the changelog-reading everyone
claims to do.

Click 3 — on screen: a single 🔁 emoji over the caption "SAME BUG,
DIFFERENT DAY", with a small line underneath: "Update the client. By
hand. Every single time." The meaning: someone, somewhere, is
manually keeping N clients in sync with 1 API by hand, forever —
not a one-time cost, a permanent tax on every future API change.

Land the transition: underneath all three is the same root cause —
the backend and its hand-written clients drift apart, and nothing
tells you until it breaks in production. That's exactly the gap the
pipeline in the next slide closes.
-->

---
layout: default
class: 'bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-6" style="color: #fff;">The fix: generate the client, don't hand-write it</h1>

<div class="flex items-start justify-center gap-4 mt-32">

<div class="flex flex-col items-center gap-3 w-36">
<div class="w-16 h-16 rounded-2xl bg-neutral-900 border border-neutral-800 flex items-center justify-center text-neutral-300 text-xl font-mono">{ }</div>
<div class="text-neutral-300 text-sm text-center leading-snug">You build the API<br/><span class="text-neutral-500 text-xs">Micronaut + Kotlin</span></div>
</div>

<v-click>
<div class="flex items-start gap-4">

<svg class="w-28 h-6 shrink-0 mt-5" viewBox="0 0 120 24" xmlns="http://www.w3.org/2000/svg">
<defs>
<marker id="arrow-fix-1" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
</defs>
<path class="flow-line" d="M2,12 H116" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-fix-1)"/>
<circle r="3" class="flow-dot dot-fix" fill="#60a5fa"/>
</svg>

<div class="flex flex-col items-center gap-3 w-36">
<div class="w-16 h-16 rounded-2xl bg-neutral-900 border border-neutral-800 flex items-center justify-center text-neutral-300">
<svg class="w-7 h-7" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14.5 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V7.5L14.5 2z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><line x1="10" y1="9" x2="8" y2="9"/></svg>
</div>
<div class="text-neutral-300 text-sm text-center leading-snug">Spec falls out<br/><span class="text-neutral-500 text-xs">openapi.yaml — a build artifact</span></div>
</div>

</div>
</v-click>

<v-click>
<div class="flex items-start gap-4">

<svg class="w-28 h-6 shrink-0 mt-5" viewBox="0 0 120 24" xmlns="http://www.w3.org/2000/svg">
<defs>
<marker id="arrow-fix-2" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
</defs>
<path class="flow-line" d="M2,12 H116" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-fix-2)"/>
<circle r="3" class="flow-dot dot-fix" fill="#60a5fa"/>
</svg>

<div class="flex flex-col items-center gap-3">
<div class="w-16 h-16 rounded-2xl bg-emerald-950 border border-emerald-900 flex items-center justify-center text-emerald-400">
<svg class="w-7 h-7" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg>
</div>
<div class="text-emerald-400 text-sm text-center leading-snug">openapi-generator<br/><span class="text-neutral-500 text-xs">OSS CLI · 50+ language generators</span></div>
</div>

<svg class="shrink-0" style="width: 74px; height: 200px; margin-top: -68px;" viewBox="0 0 74 200" xmlns="http://www.w3.org/2000/svg">
<defs>
<marker id="arrow-fan-py" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
<marker id="arrow-fan-go" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
<marker id="arrow-fan-ts" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
</defs>
<path class="flow-line" d="M0,100 H14" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6"/>
<path class="flow-line" d="M14,28 V172" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6"/>
<path class="flow-line" d="M14,28 H70" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-fan-py)"/>
<path class="flow-line" d="M14,100 H70" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-fan-go)"/>
<path class="flow-line" d="M14,172 H70" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-fan-ts)"/>
<circle r="3" class="flow-dot dot-fan-py" fill="#60a5fa"/>
<circle r="3" class="flow-dot dot-fan-go" fill="#60a5fa"/>
<circle r="3" class="flow-dot dot-fan-ts" fill="#60a5fa"/>
</svg>

<div class="flex flex-col justify-between" style="height: 200px; margin-top: -68px;">
<div class="w-14 h-14 rounded-xl bg-neutral-900 border border-neutral-800 flex items-center justify-center">
<svg class="w-8 h-8" viewBox="0 0 24 24" fill="#3776AB"><path d="M14.25.18l.9.2.73.26.59.3.45.32.34.34.25.34.16.33.1.3.04.26.02.2-.01.13V8.5l-.05.63-.13.55-.21.46-.26.38-.3.31-.33.25-.35.19-.35.14-.33.1-.3.07-.26.04-.21.02H8.77l-.69.05-.59.14-.5.22-.41.27-.33.32-.27.35-.2.36-.15.37-.1.35-.07.32-.04.27-.02.21v3.06H3.17l-.21-.03-.28-.07-.32-.12-.35-.18-.36-.26-.36-.36-.35-.46-.32-.59-.28-.73-.21-.88-.14-1.05-.05-1.23.06-1.22.16-1.04.24-.87.32-.71.36-.57.4-.44.42-.33.42-.24.4-.16.36-.1.32-.05.24-.01h.16l.06.01h8.16v-.83H6.18l-.01-2.75-.02-.37.05-.34.11-.31.17-.28.25-.26.31-.23.38-.2.44-.18.51-.15.58-.12.64-.1.71-.06.77-.04.84-.02 1.27.05zm-6.3 1.98l-.23.33-.08.41.08.41.23.34.33.22.41.09.41-.09.33-.22.23-.34.08-.41-.08-.41-.23-.33-.33-.22-.41-.09-.41.09zm13.09 3.95l.28.06.32.12.35.18.36.27.36.35.35.47.32.59.28.73.21.88.14 1.04.05 1.23-.06 1.23-.16 1.04-.24.86-.32.71-.36.57-.4.45-.42.33-.42.24-.4.16-.36.09-.32.05-.24.02-.16-.01h-8.22v.82h5.84l.01 2.76.02.36-.05.34-.11.31-.17.29-.25.25-.31.24-.38.2-.44.17-.51.15-.58.13-.64.09-.71.07-.77.04-.84.01-1.27-.04-1.07-.14-.9-.2-.73-.25-.59-.3-.45-.33-.34-.34-.25-.34-.16-.33-.1-.3-.04-.25-.02-.2.01-.13v-5.34l.05-.64.13-.54.21-.46.26-.38.3-.32.33-.24.35-.2.35-.14.33-.1.3-.06.26-.04.21-.02.13-.01h5.84l.69-.05.59-.14.5-.21.41-.28.33-.32.27-.35.2-.36.15-.36.1-.35.07-.32.04-.28.02-.21V6.07h2.09l.14.01zm-6.47 14.25l-.23.33-.08.41.08.41.23.33.33.23.41.08.41-.08.33-.23.23-.33.08-.41-.08-.41-.23-.33-.33-.23-.41-.08-.41.08z"/></svg>
</div>
<div class="w-14 h-14 rounded-xl bg-neutral-900 border border-neutral-800 flex items-center justify-center">
<svg class="w-8 h-8" viewBox="0 0 24 24" fill="#00ADD8"><path d="M1.811 10.231c-.047 0-.058-.023-.035-.059l.246-.315c.023-.035.081-.058.128-.058h4.172c.046 0 .058.035.035.07l-.199.303c-.023.036-.082.07-.117.07zM.047 11.306c-.047 0-.059-.023-.035-.058l.245-.316c.023-.035.082-.058.129-.058h5.328c.047 0 .07.035.058.07l-.093.28c-.012.047-.058.07-.105.07zm2.828 1.075c-.047 0-.059-.035-.035-.07l.163-.292c.023-.035.07-.07.117-.07h2.337c.047 0 .07.035.07.082l-.023.28c0 .047-.047.082-.082.082zm12.129-2.36c-.736.187-1.239.327-1.963.514-.176.046-.187.058-.34-.117-.174-.199-.303-.327-.548-.444-.737-.362-1.45-.257-2.115.175-.795.514-1.204 1.274-1.192 2.22.011.935.654 1.706 1.577 1.835.795.105 1.46-.175 1.987-.77.105-.13.198-.27.315-.434H10.47c-.245 0-.304-.152-.222-.35.152-.362.432-.97.596-1.274a.315.315 0 01.292-.187h4.253c-.023.316-.023.631-.07.947a4.983 4.983 0 01-.958 2.29c-.841 1.11-1.94 1.8-3.33 1.986-1.145.152-2.209-.07-3.143-.77-.865-.655-1.356-1.52-1.484-2.595-.152-1.274.222-2.419.993-3.424.83-1.086 1.928-1.776 3.272-2.02 1.098-.2 2.15-.07 3.096.571.62.41 1.063.97 1.356 1.648.07.105.023.164-.117.2m3.868 6.461c-1.064-.024-2.034-.328-2.852-1.029a3.665 3.665 0 01-1.262-2.255c-.21-1.32.152-2.489.947-3.529.853-1.122 1.881-1.706 3.272-1.95 1.192-.21 2.314-.095 3.33.595.923.63 1.496 1.484 1.648 2.605.198 1.578-.257 2.863-1.344 3.962-.771.783-1.718 1.273-2.805 1.495-.315.06-.63.07-.934.106zm2.78-4.72c-.011-.153-.011-.27-.034-.387-.21-1.157-1.274-1.81-2.384-1.554-1.087.245-1.788.935-2.045 2.033-.21.912.234 1.835 1.075 2.21.643.28 1.285.244 1.905-.07.923-.48 1.425-1.228 1.484-2.233z"/></svg>
</div>
<div class="w-14 h-14 rounded-xl bg-neutral-900 border border-neutral-800 flex items-center justify-center">
<svg class="w-8 h-8" viewBox="0 0 24 24"><rect x="2" y="2" width="20" height="20" rx="3" fill="#fff"/><path fill="#3178C6" d="M1.125 0C.502 0 0 .502 0 1.125v21.75C0 23.498.502 24 1.125 24h21.75c.623 0 1.125-.502 1.125-1.125V1.125C24 .502 23.498 0 22.875 0zm17.363 9.75c.612 0 1.154.037 1.627.111a6.38 6.38 0 0 1 1.306.34v2.458a3.95 3.95 0 0 0-.643-.361 5.093 5.093 0 0 0-.717-.26 5.453 5.453 0 0 0-1.426-.2c-.3 0-.573.028-.819.086a2.1 2.1 0 0 0-.623.242c-.17.104-.3.229-.393.374a.888.888 0 0 0-.14.49c0 .196.053.373.156.529.104.156.252.304.443.444s.423.276.696.41c.273.135.582.274.926.416.47.197.892.407 1.266.628.374.222.695.473.963.753.268.279.472.598.614.957.142.359.214.776.214 1.253 0 .657-.125 1.21-.373 1.656a3.033 3.033 0 0 1-1.012 1.085 4.38 4.38 0 0 1-1.487.596c-.566.12-1.163.18-1.79.18a9.916 9.916 0 0 1-1.84-.164 5.544 5.544 0 0 1-1.512-.493v-2.63a5.033 5.033 0 0 0 3.237 1.2c.333 0 .624-.03.872-.09.249-.06.456-.144.623-.25.166-.108.29-.234.373-.38a1.023 1.023 0 0 0-.074-1.089 2.12 2.12 0 0 0-.537-.5 5.597 5.597 0 0 0-.807-.444 27.72 27.72 0 0 0-1.007-.436c-.918-.383-1.602-.852-2.053-1.405-.45-.553-.676-1.222-.676-2.005 0-.614.123-1.141.369-1.582.246-.441.58-.804 1.004-1.089a4.494 4.494 0 0 1 1.47-.629 7.536 7.536 0 0 1 1.77-.201zm-15.113.188h9.563v2.166H9.506v9.646H6.789v-9.646H3.375z"/></svg>
</div>
</div>

</div>
</v-click>

</div>

<v-click>

<div class="text-center mt-10 text-neutral-200">

**If the API changes in a way that breaks a client, CI fails instead of a consumer breaking silently in prod.**

</div>

</v-click>

<style scoped>
.slidev-vclick-target {
  transform: translateX(16px);
}
.slidev-vclick-target:not(.slidev-vclick-hidden) {
  transform: translateX(0);
}
.flow-line {
  animation: dash-flow-fix 0.8s linear infinite;
}
@keyframes dash-flow-fix {
  to { stroke-dashoffset: -12; }
}
.flow-dot {
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}
.dot-fix {
  offset-path: path('M2,12 H116');
  animation-name: travel-fix;
  animation-duration: 2.2s;
}
@keyframes travel-fix {
  0% { offset-distance: 0%; opacity: 0; }
  10% { opacity: 1; }
  85% { opacity: 1; }
  100% { offset-distance: 100%; opacity: 0; }
}
.dot-fan-py {
  offset-path: path('M0,100 H14 V28 H70');
  animation-name: travel-fan;
  animation-duration: 2s;
  animation-delay: 0s;
}
.dot-fan-go {
  offset-path: path('M0,100 H70');
  animation-name: travel-fan;
  animation-duration: 2s;
  animation-delay: 0.3s;
}
.dot-fan-ts {
  offset-path: path('M0,100 H14 V172 H70');
  animation-name: travel-fan;
  animation-duration: 2s;
  animation-delay: 0.6s;
}
@keyframes travel-fan {
  0% { offset-distance: 0%; opacity: 0; }
  8% { opacity: 1; }
  90% { opacity: 1; }
  100% { offset-distance: 100%; opacity: 0; }
}
</style>

<!--
"I'll show you how we make this process a bit easier. While you build
the API, generate the OpenAPI spec — and that spec is what generates
the clients, through openapi-generator."

Let the connecting lines run for a second before you talk over them —
they're not just an arrangement, they're continuous. That's the visual
argument: one file, flowing constantly into every client, not a
one-time hand-off anyone can forget to redo.

Click 1: the spec falls out of the API you're already writing — not a
separate doc, a build artifact.

Click 2: openapi-generator fans that one spec out into Python, Go, and
TypeScript clients — nobody hand-writes any of the three. Worth a
sentence on the tool itself: it's a mature open-source CLI (a fork of
Swagger Codegen), not something we built — same spec in, ~50+ language
generators to choose from. We run it via the official
`openapitools/openapi-generator-cli` Docker image, pinned to v7.23.0,
so it's byte-for-byte identical locally and in CI.

Land on the punchline: because every client traces back to the same
spec, a breaking API change now fails CI instead of breaking a
consumer silently in prod. One source of truth, zero drift — "which
client is right" stops being a question anyone has to ask. That's the
whole talk in one animation; the rest of the deck is this, in more
detail, live — next up, the actual demo project.
-->

---
layout: default
class: 'bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-2" style="color: #fff;">The demo project</h1>

<div class="text-center text-neutral-400 mb-8">A small <span class="text-neutral-200 font-medium">Dataset Catalog API</span> — Micronaut + Kotlin</div>

<div class="grid grid-cols-2 gap-5 max-w-4xl mx-auto">

<v-click>

<div class="rounded-2xl p-5 bg-neutral-900 border border-neutral-800 flex gap-4 items-start">
<div class="w-11 h-11 shrink-0 rounded-xl bg-neutral-700 border border-neutral-600 flex items-center justify-center text-neutral-200">
<svg class="w-6 h-6" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14.5 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V7.5L14.5 2z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><line x1="10" y1="9" x2="8" y2="9"/></svg>
</div>
<div>
<div class="text-neutral-100 font-mono font-medium">Application.kt</div>
<div class="text-neutral-400 text-sm mt-1">Bootstraps Micronaut, declares the OpenAPI info and the <span class="text-neutral-300">bearerAuth</span> security scheme</div>
</div>
</div>

</v-click>

<v-click>

<div class="rounded-2xl p-5 bg-indigo-950 border border-indigo-900 flex gap-4 items-start">
<div class="w-11 h-11 shrink-0 rounded-xl bg-indigo-600 border border-indigo-400 flex items-center justify-center text-white">
<svg class="w-6 h-6" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2 2 7l10 5 10-5-10-5z"/><path d="M2 17l10 5 10-5"/><path d="M2 12l10 5 10-5"/></svg>
</div>
<div>
<div class="text-indigo-100 font-mono font-medium">catalog/*DTO.kt</div>
<div class="text-indigo-300/80 text-sm mt-1">The data model — owner, tags, a <span class="text-indigo-200">sensitivity</span> enum, schema fields</div>
</div>
</div>

</v-click>

<v-click>

<div class="rounded-2xl p-5 bg-emerald-950 border border-emerald-900 flex gap-4 items-start">
<div class="w-11 h-11 shrink-0 rounded-xl bg-emerald-700 border border-emerald-500 flex items-center justify-center text-white">
<svg class="w-6 h-6" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="2" y1="12" x2="22" y2="12"/><path d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"/></svg>
</div>
<div>
<div class="text-emerald-100 font-mono font-medium">DatasetController</div>
<div class="text-emerald-300/80 text-sm mt-1"><span class="text-emerald-200">GET / POST / PUT / DELETE</span> on <span class="text-emerald-200">/datasets</span> — the actual REST surface</div>
</div>
</div>

</v-click>

<v-click>

<div class="rounded-2xl p-5 bg-amber-950 border border-amber-900 flex gap-4 items-start">
<div class="w-11 h-11 shrink-0 rounded-xl bg-amber-600 border border-amber-400 flex items-center justify-center text-white">
<svg class="w-6 h-6" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
</div>
<div>
<div class="text-amber-100 font-mono font-medium">ApiTokenFilter</div>
<div class="text-amber-300/80 text-sm mt-1">Guards <span class="text-amber-200">/datasets/**</span> — rejects POST/PUT/DELETE without the right bearer token</div>
</div>
</div>

</v-click>

</div>

<v-click>

<div class="text-center mt-8 text-neutral-300">
Four small files — that's the whole API surface this talk generates clients from.
</div>

</v-click>

<style scoped>
.slidev-vclick-target {
  transform: translateY(14px);
}
.slidev-vclick-target:not(.slidev-vclick-hidden) {
  transform: translateY(0);
}
</style>

<!--
Four quick clicks, don't over-explain any one of them — this slide is
scaffolding for the live demo, not content in its own right.

Click 1, Application.kt: this is the file with the two annotations
that make the rest of the talk possible — @OpenAPIDefinition and
@SecurityScheme. Everything downstream reads from here.

Click 2, the DTOs: a dataset has an owner, tags, a sensitivity enum
(PUBLIC/INTERNAL/RESTRICTED), and a list of schema fields. This is the
shape that gets renamed later to break something on purpose.

Click 3, DatasetController: the actual REST surface — five endpoints,
`operationId`s already named deliberately (listDatasets, getDataset,
etc. — callback to the naming point from two slides ago).

Click 4, ApiTokenFilter: reads flow freely, writes need a bearer
token. This is the split the two demo panels showed earlier.

Land on the punchline: small on purpose. Don't dwell on the file tree
— the point is just to orient the room before you start typing.
-->

---
layout: default
class: 'bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-8" style="color: #fff;">Step 1 — the spec is a build artifact, not a doc someone writes</h1>

<div class="max-w-3xl mx-auto">

```kotlin {all|1-3}
./gradlew build
# → compiles, runs tests, AND generates the OpenAPI spec

# spec lands at build/openapi/openapi.yaml
```

</div>

<style scoped>
pre {
  background-color: #18181b !important;
  border: 1px solid #27272a;
}
pre code, pre code span {
  color: #e4e4e7 !important;
}
</style>

<v-click>

<div class="flex items-center justify-center gap-4 mt-10">

<div class="flex flex-col items-center gap-3 w-44">
<div class="w-16 h-16 rounded-2xl bg-neutral-900 border border-neutral-800 flex items-center justify-center text-neutral-300">
<svg class="w-7 h-7" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="16 18 22 12 16 6"/><polyline points="8 6 2 12 8 18"/></svg>
</div>
<div class="text-neutral-300 text-sm text-center leading-snug"><span class="font-mono text-neutral-200">@Operation</span> / <span class="font-mono text-neutral-200">@Schema</span><br/><span class="text-neutral-500 text-xs">annotations on the controller</span></div>
</div>

<svg class="w-28 h-6 shrink-0" viewBox="0 0 120 24" xmlns="http://www.w3.org/2000/svg">
<defs>
<marker id="arrow-step1" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
</defs>
<path class="flow-line" d="M2,12 H116" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-step1)"/>
<circle r="3" class="flow-dot dot-step1" fill="#60a5fa"/>
</svg>

<div class="flex flex-col items-center gap-3 w-44">
<div class="w-16 h-16 rounded-2xl bg-indigo-600 border border-indigo-400 flex items-center justify-center text-white">
<svg class="w-7 h-7" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14.5 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V7.5L14.5 2z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/><line x1="10" y1="9" x2="8" y2="9"/></svg>
</div>
<div class="text-neutral-300 text-sm text-center leading-snug font-mono">openapi.yaml<br/><span class="text-neutral-500 text-xs font-sans">the spec, generated every build</span></div>
</div>

</div>

</v-click>

<v-click>

<div class="text-center mt-8 text-neutral-200">

`micronaut-openapi` reads the annotations at compile time via KSP — <strong>the spec is the code, always in sync.</strong>

</div>

</v-click>

<style scoped>
.flow-line {
  animation: dash-flow-step1 0.8s linear infinite;
}
@keyframes dash-flow-step1 {
  to { stroke-dashoffset: -12; }
}
.flow-dot {
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}
.dot-step1 {
  offset-path: path('M2,12 H116');
  animation-name: travel-step1;
  animation-duration: 2.2s;
}
@keyframes travel-step1 {
  0% { offset-distance: 0%; opacity: 0; }
  10% { opacity: 1; }
  85% { opacity: 1; }
  100% { offset-distance: 100%; opacity: 0; }
}
</style>

<!--
The key claim on this slide: the spec isn't a doc someone maintains by
hand and forgets — it falls out of a normal `./gradlew build`, derived
from annotations already on the controller.

Click 1: point at the arrow — annotations in, spec out, automatically,
every single build.

Click 2: land the line. If you rename a field, the spec changes on
the next build. No separate "update the docs" step to skip.
-->

---
layout: default
class: 'bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-6" style="color: #fff;">Step 2 — generate typed clients, in three languages</h1>

<div class="max-w-3xl mx-auto">

```bash
./gradlew generatePythonClient      # clients/python/generated
./gradlew generateTypeScriptClient  # clients/typescript/generated
./gradlew generateGoClient          # clients/go/generated
./gradlew generateAllClients        # all three
```

</div>

<style scoped>
pre {
  background-color: #18181b !important;
  border: 1px solid #27272a;
}
pre code, pre code span {
  color: #e4e4e7 !important;
}
</style>

<v-click>

<div class="flex items-center justify-center gap-4 mt-8">

<div class="flex flex-col items-center gap-3">
<div class="w-16 h-16 rounded-2xl bg-neutral-900 border border-neutral-800 flex items-center justify-center text-neutral-300 font-mono text-xs text-center leading-tight">generate<br/>AllClients</div>
</div>

<svg class="shrink-0" style="width: 74px; height: 200px;" viewBox="0 0 74 200" xmlns="http://www.w3.org/2000/svg">
<defs>
<marker id="arrow-step2-py" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
<marker id="arrow-step2-go" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
<marker id="arrow-step2-ts" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
</defs>
<path class="flow-line" d="M0,100 H14" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6"/>
<path class="flow-line" d="M14,28 V172" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6"/>
<path class="flow-line" d="M14,28 H70" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-step2-py)"/>
<path class="flow-line" d="M14,100 H70" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-step2-go)"/>
<path class="flow-line" d="M14,172 H70" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-step2-ts)"/>
<circle r="3" class="flow-dot dot-step2-py" fill="#60a5fa"/>
<circle r="3" class="flow-dot dot-step2-go" fill="#60a5fa"/>
<circle r="3" class="flow-dot dot-step2-ts" fill="#60a5fa"/>
</svg>

<div class="flex flex-col gap-4">
<div class="w-14 h-14 rounded-xl bg-neutral-900 border border-neutral-800 flex items-center justify-center">
<svg class="w-8 h-8" viewBox="0 0 24 24" fill="#3776AB"><path d="M14.25.18l.9.2.73.26.59.3.45.32.34.34.25.34.16.33.1.3.04.26.02.2-.01.13V8.5l-.05.63-.13.55-.21.46-.26.38-.3.31-.33.25-.35.19-.35.14-.33.1-.3.07-.26.04-.21.02H8.77l-.69.05-.59.14-.5.22-.41.27-.33.32-.27.35-.2.36-.15.37-.1.35-.07.32-.04.27-.02.21v3.06H3.17l-.21-.03-.28-.07-.32-.12-.35-.18-.36-.26-.36-.36-.35-.46-.32-.59-.28-.73-.21-.88-.14-1.05-.05-1.23.06-1.22.16-1.04.24-.87.32-.71.36-.57.4-.44.42-.33.42-.24.4-.16.36-.1.32-.05.24-.01h.16l.06.01h8.16v-.83H6.18l-.01-2.75-.02-.37.05-.34.11-.31.17-.28.25-.26.31-.23.38-.2.44-.18.51-.15.58-.12.64-.1.71-.06.77-.04.84-.02 1.27.05zm-6.3 1.98l-.23.33-.08.41.08.41.23.34.33.22.41.09.41-.09.33-.22.23-.34.08-.41-.08-.41-.23-.33-.33-.22-.41-.09-.41.09zm13.09 3.95l.28.06.32.12.35.18.36.27.36.35.35.47.32.59.28.73.21.88.14 1.04.05 1.23-.06 1.23-.16 1.04-.24.86-.32.71-.36.57-.4.45-.42.33-.42.24-.4.16-.36.09-.32.05-.24.02-.16-.01h-8.22v.82h5.84l.01 2.76.02.36-.05.34-.11.31-.17.29-.25.25-.31.24-.38.2-.44.17-.51.15-.58.13-.64.09-.71.07-.77.04-.84.01-1.27-.04-1.07-.14-.9-.2-.73-.25-.59-.3-.45-.33-.34-.34-.25-.34-.16-.33-.1-.3-.04-.25-.02-.2.01-.13v-5.34l.05-.64.13-.54.21-.46.26-.38.3-.32.33-.24.35-.2.35-.14.33-.1.3-.06.26-.04.21-.02.13-.01h5.84l.69-.05.59-.14.5-.21.41-.28.33-.32.27-.35.2-.36.15-.36.1-.35.07-.32.04-.28.02-.21V6.07h2.09l.14.01zm-6.47 14.25l-.23.33-.08.41.08.41.23.33.33.23.41.08.41-.08.33-.23.23-.33.08-.41-.08-.41-.23-.33-.33-.23-.41-.08-.41.08z"/></svg>
</div>
<div class="w-14 h-14 rounded-xl bg-neutral-900 border border-neutral-800 flex items-center justify-center">
<svg class="w-8 h-8" viewBox="0 0 24 24" fill="#00ADD8"><path d="M1.811 10.231c-.047 0-.058-.023-.035-.059l.246-.315c.023-.035.081-.058.128-.058h4.172c.046 0 .058.035.035.07l-.199.303c-.023.036-.082.07-.117.07zM.047 11.306c-.047 0-.059-.023-.035-.058l.245-.316c.023-.035.082-.058.129-.058h5.328c.047 0 .07.035.058.07l-.093.28c-.012.047-.058.07-.105.07zm2.828 1.075c-.047 0-.059-.035-.035-.07l.163-.292c.023-.035.07-.07.117-.07h2.337c.047 0 .07.035.07.082l-.023.28c0 .047-.047.082-.082.082zm12.129-2.36c-.736.187-1.239.327-1.963.514-.176.046-.187.058-.34-.117-.174-.199-.303-.327-.548-.444-.737-.362-1.45-.257-2.115.175-.795.514-1.204 1.274-1.192 2.22.011.935.654 1.706 1.577 1.835.795.105 1.46-.175 1.987-.77.105-.13.198-.27.315-.434H10.47c-.245 0-.304-.152-.222-.35.152-.362.432-.97.596-1.274a.315.315 0 01.292-.187h4.253c-.023.316-.023.631-.07.947a4.983 4.983 0 01-.958 2.29c-.841 1.11-1.94 1.8-3.33 1.986-1.145.152-2.209-.07-3.143-.77-.865-.655-1.356-1.52-1.484-2.595-.152-1.274.222-2.419.993-3.424.83-1.086 1.928-1.776 3.272-2.02 1.098-.2 2.15-.07 3.096.571.62.41 1.063.97 1.356 1.648.07.105.023.164-.117.2m3.868 6.461c-1.064-.024-2.034-.328-2.852-1.029a3.665 3.665 0 01-1.262-2.255c-.21-1.32.152-2.489.947-3.529.853-1.122 1.881-1.706 3.272-1.95 1.192-.21 2.314-.095 3.33.595.923.63 1.496 1.484 1.648 2.605.198 1.578-.257 2.863-1.344 3.962-.771.783-1.718 1.273-2.805 1.495-.315.06-.63.07-.934.106zm2.78-4.72c-.011-.153-.011-.27-.034-.387-.21-1.157-1.274-1.81-2.384-1.554-1.087.245-1.788.935-2.045 2.033-.21.912.234 1.835 1.075 2.21.643.28 1.285.244 1.905-.07.923-.48 1.425-1.228 1.484-2.233z"/></svg>
</div>
<div class="w-14 h-14 rounded-xl bg-neutral-900 border border-neutral-800 flex items-center justify-center">
<svg class="w-8 h-8" viewBox="0 0 24 24"><rect x="2" y="2" width="20" height="20" rx="3" fill="#fff"/><path fill="#3178C6" d="M1.125 0C.502 0 0 .502 0 1.125v21.75C0 23.498.502 24 1.125 24h21.75c.623 0 1.125-.502 1.125-1.125V1.125C24 .502 23.498 0 22.875 0zm17.363 9.75c.612 0 1.154.037 1.627.111a6.38 6.38 0 0 1 1.306.34v2.458a3.95 3.95 0 0 0-.643-.361 5.093 5.093 0 0 0-.717-.26 5.453 5.453 0 0 0-1.426-.2c-.3 0-.573.028-.819.086a2.1 2.1 0 0 0-.623.242c-.17.104-.3.229-.393.374a.888.888 0 0 0-.14.49c0 .196.053.373.156.529.104.156.252.304.443.444s.423.276.696.41c.273.135.582.274.926.416.47.197.892.407 1.266.628.374.222.695.473.963.753.268.279.472.598.614.957.142.359.214.776.214 1.253 0 .657-.125 1.21-.373 1.656a3.033 3.033 0 0 1-1.012 1.085 4.38 4.38 0 0 1-1.487.596c-.566.12-1.163.18-1.79.18a9.916 9.916 0 0 1-1.84-.164 5.544 5.544 0 0 1-1.512-.493v-2.63a5.033 5.033 0 0 0 3.237 1.2c.333 0 .624-.03.872-.09.249-.06.456-.144.623-.25.166-.108.29-.234.373-.38a1.023 1.023 0 0 0-.074-1.089 2.12 2.12 0 0 0-.537-.5 5.597 5.597 0 0 0-.807-.444 27.72 27.72 0 0 0-1.007-.436c-.918-.383-1.602-.852-2.053-1.405-.45-.553-.676-1.222-.676-2.005 0-.614.123-1.141.369-1.582.246-.441.58-.804 1.004-1.089a4.494 4.494 0 0 1 1.47-.629 7.536 7.536 0 0 1 1.77-.201zm-15.113.188h9.563v2.166H9.506v9.646H6.789v-9.646H3.375z"/></svg>
</div>
</div>

</div>

</v-click>

<style scoped>
.flow-line {
  animation: dash-flow-step2 0.8s linear infinite;
}
@keyframes dash-flow-step2 {
  to { stroke-dashoffset: -12; }
}
.flow-dot {
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}
.dot-step2-py {
  offset-path: path('M0,100 H14 V28 H70');
  animation-name: travel-step2;
  animation-duration: 2s;
  animation-delay: 0s;
}
.dot-step2-go {
  offset-path: path('M0,100 H70');
  animation-name: travel-step2;
  animation-duration: 2s;
  animation-delay: 0.3s;
}
.dot-step2-ts {
  offset-path: path('M0,100 H14 V172 H70');
  animation-name: travel-step2;
  animation-duration: 2s;
  animation-delay: 0.6s;
}
@keyframes travel-step2 {
  0% { offset-distance: 0%; opacity: 0; }
  8% { opacity: 1; }
  90% { opacity: 1; }
  100% { offset-distance: 100%; opacity: 0; }
}
</style>

<div class="max-w-3xl mx-auto mt-6 text-sm text-neutral-400">

- Shells out to the official `openapitools/openapi-generator-cli` Docker image
- Same command, identical output locally and in CI
- Class/method names come straight from `@Operation(operationId = "...")`

</div>

<!--
Emphasize the Docker-based generator: it's the *same* command locally
and in CI, so "works on my machine" doesn't apply to codegen output —
that's why the animation fans out identically every time.

Mention `operationId` deliberately — it's the one place a developer's
naming choice leaks into every generated client, so it's worth naming
things well (`listDatasets`, not `list`).
-->

---
layout: default
class: 'bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-2" style="color: #fff;">Step 3 — generated code isn't always perfect</h1>

<div class="text-center text-neutral-400 mb-8">openapi-generator's <span class="text-neutral-200 font-medium">Python</span> client doesn't reliably attach the bearer token to outgoing requests</div>

<div class="flex items-center justify-center gap-6">

<div class="flex flex-col items-center gap-3 w-40">
<div class="w-16 h-16 rounded-2xl bg-rose-950 border border-rose-900 flex items-center justify-center text-rose-400">
<svg class="w-7 h-7" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="15" y1="9" x2="9" y2="15"/><line x1="9" y1="9" x2="15" y2="15"/></svg>
</div>
<div class="text-rose-300 text-sm text-center leading-snug">Generated client<br/><span class="text-neutral-500 text-xs">missing the bearer header</span></div>
</div>

<v-click>
<div class="flex items-center gap-6">

<svg class="w-24 h-6 shrink-0" viewBox="0 0 100 24" xmlns="http://www.w3.org/2000/svg">
<defs>
<marker id="arrow-step3" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
</defs>
<path class="flow-line" d="M2,12 H96" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-step3)"/>
<circle r="3" class="flow-dot dot-step3" fill="#60a5fa"/>
</svg>

<div class="flex flex-col items-center gap-3 w-48">
<div class="w-16 h-16 rounded-2xl bg-neutral-900 border border-neutral-800 flex items-center justify-center text-neutral-300 spin-slow">
<svg class="w-7 h-7" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 2v6h-6"/><path d="M3 12a9 9 0 0 1 15-6.7L21 8"/><path d="M3 12a9 9 0 0 0 15 6.7L21 16"/><path d="M21 22v-6h-6"/></svg>
</div>
<div class="text-neutral-300 text-sm text-center leading-snug font-mono">patch_python_client.py<br/><span class="text-neutral-500 text-xs font-sans">idempotent — safe to re-run</span></div>
</div>

</div>
</v-click>

<v-click>
<div class="flex items-center gap-6">

<svg class="w-24 h-6 shrink-0" viewBox="0 0 100 24" xmlns="http://www.w3.org/2000/svg">
<defs>
<marker id="arrow-step3b" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
<path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/>
</marker>
</defs>
<path class="flow-line" d="M2,12 H96" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="6 6" marker-end="url(#arrow-step3b)"/>
<circle r="3" class="flow-dot dot-step3b" fill="#60a5fa"/>
</svg>

<div class="flex flex-col items-center gap-3 w-40">
<div class="w-16 h-16 rounded-2xl bg-emerald-950 border border-emerald-900 flex items-center justify-center text-emerald-400">
<svg class="w-7 h-7" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 6 9 17l-5-5"/></svg>
</div>
<div class="text-emerald-300 text-sm text-center leading-snug">Patched client<br/><span class="text-neutral-500 text-xs">header honored every request</span></div>
</div>

</div>
</v-click>

</div>

<v-click>

<div class="text-center mt-10 text-neutral-200">

**Codegen gets you 95% there. Own the last 5% explicitly, in a script CI runs too.**

</div>

</v-click>

<style scoped>
.flow-line {
  animation: dash-flow-step3 0.8s linear infinite;
}
@keyframes dash-flow-step3 {
  to { stroke-dashoffset: -12; }
}
.flow-dot {
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}
.dot-step3 {
  offset-path: path('M2,12 H96');
  animation-name: travel-step3;
  animation-duration: 1.8s;
}
.dot-step3b {
  offset-path: path('M2,12 H96');
  animation-name: travel-step3;
  animation-duration: 1.8s;
}
@keyframes travel-step3 {
  0% { offset-distance: 0%; opacity: 0; }
  10% { opacity: 1; }
  85% { opacity: 1; }
  100% { offset-distance: 100%; opacity: 0; }
}
.spin-slow svg {
  animation: spin-slow 4s linear infinite;
}
@keyframes spin-slow {
  to { transform: rotate(360deg); }
}
</style>

<!--
This is the "honesty" slide — codegen isn't magic, and admitting that
builds credibility. Name the actual bug: `Configuration.access_token`
/ `auth_settings()` doesn't reliably attach the bearer token in the
generated Python client.

Click 1: the patch script — `ApiClient.set_default_header(...)`,
honored on every request. Point at the spinning icon: this has to
survive being run after every regeneration, forever.

Click 2: the patched client, header attached, no human has to
remember to check it first.

Land on the punchline: codegen gets you 95% there, own the last 5%
explicitly.
-->

---
layout: default
class: 'bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-10" style="color: #fff;">Step 4 — wire it all into CI</h1>

<div class="flex items-center justify-center gap-1.5">

<div class="rounded-xl px-3 py-3 bg-neutral-900 border border-neutral-800 text-center w-24">
<div class="text-neutral-100 text-sm font-medium">build</div>
</div>

<svg class="w-8 h-5 shrink-0" viewBox="0 0 32 20" xmlns="http://www.w3.org/2000/svg">
<defs><marker id="arrow-p1" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="5" markerHeight="5" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/></marker></defs>
<path class="flow-line" d="M2,10 H26" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="5 5" marker-end="url(#arrow-p1)"/>
<circle r="2.5" class="flow-dot dot-p1" fill="#60a5fa"/>
</svg>

<div class="rounded-xl px-3 py-3 bg-indigo-950 border border-indigo-900 text-center w-24">
<div class="text-indigo-200 text-sm font-medium">generate spec</div>
</div>

<svg class="w-8 h-5 shrink-0" viewBox="0 0 32 20" xmlns="http://www.w3.org/2000/svg">
<defs><marker id="arrow-p2" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="5" markerHeight="5" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/></marker></defs>
<path class="flow-line" d="M2,10 H26" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="5 5" marker-end="url(#arrow-p2)"/>
<circle r="2.5" class="flow-dot dot-p2" fill="#60a5fa"/>
</svg>

<div class="rounded-xl px-3 py-3 bg-neutral-900 border border-neutral-800 text-center w-28">
<div class="text-neutral-100 text-sm font-medium">generate 3 clients</div>
</div>

<svg class="w-8 h-5 shrink-0" viewBox="0 0 32 20" xmlns="http://www.w3.org/2000/svg">
<defs><marker id="arrow-p3" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="5" markerHeight="5" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/></marker></defs>
<path class="flow-line" d="M2,10 H26" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="5 5" marker-end="url(#arrow-p3)"/>
<circle r="2.5" class="flow-dot dot-p3" fill="#60a5fa"/>
</svg>

<div class="rounded-xl px-3 py-3 bg-amber-950 border border-amber-900 text-center w-28">
<div class="text-amber-200 text-sm font-medium">patch (automatic)</div>
</div>

<svg class="w-8 h-5 shrink-0" viewBox="0 0 32 20" xmlns="http://www.w3.org/2000/svg">
<defs><marker id="arrow-p4" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="5" markerHeight="5" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/></marker></defs>
<path class="flow-line" d="M2,10 H26" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="5 5" marker-end="url(#arrow-p4)"/>
<circle r="2.5" class="flow-dot dot-p4" fill="#60a5fa"/>
</svg>

<div class="rounded-xl px-3 py-3 bg-neutral-900 border border-neutral-800 text-center w-24">
<div class="text-neutral-100 text-sm font-medium">run app</div>
</div>

<svg class="w-8 h-5 shrink-0" viewBox="0 0 32 20" xmlns="http://www.w3.org/2000/svg">
<defs><marker id="arrow-p5" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="5" markerHeight="5" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/></marker></defs>
<path class="flow-line" d="M2,10 H26" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="5 5" marker-end="url(#arrow-p5)"/>
<circle r="2.5" class="flow-dot dot-p5" fill="#60a5fa"/>
</svg>

<div class="rounded-xl px-3 py-3 bg-neutral-900 border border-neutral-800 text-center w-32">
<div class="text-neutral-100 text-sm font-medium">pytest integration suite</div>
</div>

<svg class="w-8 h-5 shrink-0" viewBox="0 0 32 20" xmlns="http://www.w3.org/2000/svg">
<defs><marker id="arrow-p6" viewBox="0 0 10 10" refX="7" refY="5" markerWidth="5" markerHeight="5" orient="auto-start-reverse"><path d="M0,0 L10,5 L0,10" fill="none" stroke="#a1a1aa" stroke-width="2"/></marker></defs>
<path class="flow-line" d="M2,10 H26" fill="none" stroke="#71717a" stroke-width="2" stroke-dasharray="5 5" marker-end="url(#arrow-p6)"/>
<circle r="2.5" class="flow-dot dot-p6" fill="#60a5fa"/>
</svg>

<div class="rounded-xl px-3 py-3 bg-emerald-950 border border-emerald-900 text-center w-32">
<div class="text-emerald-200 text-sm font-medium">open PR → client repos</div>
</div>

</div>

<div class="text-center mt-10 text-neutral-400 text-sm">
<span class="font-mono text-neutral-300">.github/workflows/api-client-pipeline.yml</span> runs build → test on every push/PR to <span class="font-mono text-neutral-300">main</span>; the publish step runs only after a merge
</div>

<div class="text-center mt-3 text-neutral-200">
The integration suite drives the <strong>real generated+patched client</strong> — not a mock — against a live instance of the app.
</div>

<div class="text-center mt-2 text-neutral-200">
Once merged, a fourth job regenerates all three clients and pushes each to its own <span class="font-mono text-sm text-neutral-300">catalog-client-&lt;lang&gt;</span> repo as a PR — the owning team reviews the sync instead of it landing silently.
</div>

<style scoped>
.flow-line {
  animation: dash-flow-p 0.8s linear infinite;
}
@keyframes dash-flow-p {
  to { stroke-dashoffset: -10; }
}
.flow-dot {
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}
.dot-p1, .dot-p2, .dot-p3, .dot-p4, .dot-p5, .dot-p6 {
  offset-path: path('M2,10 H26');
  animation-name: travel-p;
  animation-duration: 1s;
}
.dot-p1 { animation-delay: 0s; }
.dot-p2 { animation-delay: 0.2s; }
.dot-p3 { animation-delay: 0.4s; }
.dot-p4 { animation-delay: 0.6s; }
.dot-p5 { animation-delay: 0.8s; }
.dot-p6 { animation-delay: 1s; }
@keyframes travel-p {
  0% { offset-distance: 0%; opacity: 0; }
  15% { opacity: 1; }
  80% { opacity: 1; }
  100% { offset-distance: 100%; opacity: 0; }
}
</style>

<!--
Point along the diagram left to right — this is Steps 1–3 chained
into one pipeline, running on every push/PR to main. The one thing to
land: the integration suite exercises the *real* generated+patched
client, not a hand-written stand-in — so this test can only pass if
the actual thing a consumer would install still works.

The last box is new: once a change merges to main and all three
verification jobs pass, a fourth job — publish-clients — regenerates
every client fresh and pushes it to its own catalog-client-<lang> repo
as a PR. Point out it's gated on the merge event specifically (`if:
github.event_name == 'push' && github.ref == 'refs/heads/main'`), so it
never fires on a PR build — only once a change has actually landed.
Auth is a repo secret (CLIENT_REPOS_TOKEN); the default GITHUB_TOKEN
can't push or open PRs on other repos.

Land the transition explicitly: "this isn't a diagram, it's the actual
workflow in this repo — let's break something for real and watch it
happen." That line is the cue to switch to the terminal for the live
demo.
-->

---
layout: default
class: 'bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-6" style="color: #fff;">Show, don't tell: break something</h1>

<div class="flex justify-center gap-3 mb-4 text-xs">
<span class="px-2 py-1 rounded bg-neutral-800 text-neutral-400">1 API</span>
<span class="px-2 py-1 rounded bg-neutral-800 text-neutral-400">2 break</span>
<span class="px-2 py-1 rounded bg-neutral-800 text-neutral-400">3 build+spec</span>
<span class="px-2 py-1 rounded bg-neutral-800 text-neutral-400">4 generate</span>
<span class="px-2 py-1 rounded bg-neutral-800 text-neutral-400">5 test</span>
<span class="px-2 py-1 rounded bg-neutral-800 text-neutral-400">6 CI</span>
</div>

<div class="max-w-2xl mx-auto">

```bash {1-2|3|4-6|7-8|9}
# 1. API already running — localhost:8080/swagger-ui
# 2. Rename a field in DatasetDTO.kt — a breaking change

./gradlew build                       # 3. rebuild + regenerate the spec

./gradlew generatePythonClient        # 4. regenerate + install
pip install --force-reinstall --no-deps ./clients/python/generated

pytest tests/integration -v           # 5. run integration tests
```

</div>

<style scoped>
pre {
  background-color: #18181b !important;
  border: 1px solid #27272a;
}
pre code, pre code span {
  color: #e4e4e7 !important;
}
</style>

<v-click>

<div class="flex justify-center mt-5">
<div class="rounded-full px-5 py-2 bg-rose-950 border border-rose-800 text-rose-300 font-mono font-bold text-lg pulse-fail">
🔴 FAILED
</div>
</div>

</v-click>

<v-click>

<div class="flex justify-center mt-4">
<div class="rounded-full px-5 py-2 bg-neutral-900 border border-neutral-700 text-neutral-300 font-mono text-sm">
6. git push && gh pr create — check GitHub Actions →
</div>
</div>

</v-click>

<div class="text-center mt-6 text-neutral-400 text-sm max-w-2xl mx-auto">
Class and method names in the test come from the <strong class="text-neutral-200">generated</strong> client —
if the shape changes, the test collection itself breaks.
</div>

<style scoped>
.pulse-fail {
  animation: pulse-fail 1.4s ease-in-out infinite;
}
@keyframes pulse-fail {
  0%, 100% { opacity: 1; transform: scale(1); }
  50% { opacity: 0.75; transform: scale(1.04); }
}
</style>

<!--
SETUP before you start talking (not part of the timed segment):
- Terminal A, running in the background the whole time: `./gradlew run`
  — the app on :8080. pytest defaults to CATALOG_API_BASE_URL=
  http://localhost:8080, so this has to be up before step 5, and it
  has to be the CURRENT build by step 3 (see below).
- Terminal B: where you actually type, foreground the whole segment.
- Browser tab 1: `http://localhost:8080/swagger-ui`, already open.
- Branch `demo/break-something` checked out, gh CLI authenticated,
  repo is TechieRpk/javazone-2026.

THE SIX STEPS, in order — narrate all six in this order; the CI
trigger happens quietly in the background after step 2 so the wait
is hidden, not skipped:

1. SHOW THE API (~30s) — switch to the browser tab, swagger-ui is
   already loaded. Expand GET /datasets, hit "Try it out" → Execute,
   show a real JSON response. This is the "before" picture — a normal,
   working API — before anything gets broken.

2. MAKE THE BREAKING CHANGE (~30s) — switch to the editor, rename a
   field on `DatasetDTO` (e.g. `schemaFields` → `fields`), save (the
   slide's next click shows this as a comment). The MOMENT this is
   saved, in a THIRD terminal (or a background job), fire:
     git commit -am "demo: rename a field (breaking change)"
     git push -u origin demo/break-something && gh pr create --fill
   Do this quietly — don't narrate it as its own beat yet, that's
   step 6. This is the trick: GitHub Actions now has the full 2-3
   minutes of steps 3-5 to run in the background before you ever look
   at it.

3. BUILD + GENERATE THE SPEC (~30s) — advance to the next click. Kill
   Terminal A (Ctrl+C), run `./gradlew build`. Optionally cat/open
   `build/openapi/openapi.yaml` and point at the renamed field — this
   is the callback to "the spec is a build artifact" from earlier.
   Then `./gradlew run` again in Terminal A so the live server is
   running the NEW code too, not the stale pre-rename build.

4. GENERATE THE CLIENT(S) (~20s) — next click. `generatePythonClient`,
   force-reinstall. There's no separate patch command to type anymore —
   `generatePythonClient` now runs `scripts/patch_python_client.py`
   itself, right after the Docker generation step (a `doLast` on the
   Gradle task), so it can never be forgotten. Worth a half-sentence:
   "generating the client already includes patching its known gaps."
   Mention in passing that the full pipeline does the same for
   TypeScript and generates Go too (Step 4's slide) — you're only doing
   Python locally for time.

5. RUN INTEGRATION TESTS (~20s) — next click. `pytest tests/integration
   -v` against the now-restarted app — fails immediately. Point at the
   FAILED badge: "the generated client's shape changed, so the class/
   method names this test imports no longer exist — the test
   collection itself broke, not just an assertion."

6. GITHUB ACTIONS STATUS (~60-90s) — the final click reveals the
   "check GitHub Actions" badge — NOW say out loud for the first time
   that you pushed this the moment you made the change, back in
   step 2. Switch to browser tab 2, open the PR. It's had 2-3 minutes
   to run by now:
     - Finished: point at the red X on python-client-integration,
       open the failed pytest step in the log — same failure, for
       real, in CI.
     - Still running: point at the in-progress checks, say "you can
       watch this finish after the talk," narrate the last visible
       step, move on. Never stall waiting on a spinner.

Afterward: close the PR without merging, delete the branch — main was
never touched.
-->

---
layout: center
class: 'text-center bg-neutral-950'
---

<h1 class="text-3xl font-bold text-center mb-8" style="color: #fff;">Takeaways</h1>

<div class="flex flex-col gap-3 text-left max-w-xl mx-auto">

<v-click>
<div class="flex items-start gap-3 rounded-xl p-3 bg-neutral-900 border border-neutral-800">
<svg class="w-5 h-5 mt-0.5 shrink-0 text-emerald-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 6 9 17l-5-5"/></svg>
<span class="text-neutral-200">Generate clients from the spec — don't hand-write and hope</span>
</div>
</v-click>

<v-click>
<div class="flex items-start gap-3 rounded-xl p-3 bg-neutral-900 border border-neutral-800">
<svg class="w-5 h-5 mt-0.5 shrink-0 text-emerald-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 6 9 17l-5-5"/></svg>
<span class="text-neutral-200">The spec should be a build artifact, derived from annotated code</span>
</div>
</v-click>

<v-click>
<div class="flex items-start gap-3 rounded-xl p-3 bg-neutral-900 border border-neutral-800">
<svg class="w-5 h-5 mt-0.5 shrink-0 text-emerald-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 6 9 17l-5-5"/></svg>
<span class="text-neutral-200">Codegen has gaps — patch them explicitly and idempotently, don't fork the generator</span>
</div>
</v-click>

<v-click>
<div class="flex items-start gap-3 rounded-xl p-3 bg-neutral-900 border border-neutral-800">
<svg class="w-5 h-5 mt-0.5 shrink-0 text-emerald-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 6 9 17l-5-5"/></svg>
<span class="text-neutral-200">Test the <strong class="text-white">generated</strong> artifact in CI, not a hand-rolled stand-in</span>
</div>
</v-click>

<v-click>
<div class="flex items-start gap-3 rounded-xl p-3 bg-emerald-950 border border-emerald-800">
<svg class="w-5 h-5 mt-0.5 shrink-0 text-emerald-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 6 9 17l-5-5"/></svg>
<span class="text-emerald-100 font-medium">A breaking API change should fail a pipeline, not a customer's integration</span>
</div>
</v-click>

</div>

<style scoped>
.slidev-vclick-target {
  transform: translateY(10px);
}
.slidev-vclick-target:not(.slidev-vclick-hidden) {
  transform: translateY(0);
}
</style>

<!--
Don't just re-read the bullets — compress each into a half-sentence
and let the slide carry the detail, one click per row. This is also
where you can name what generalizes beyond this demo: any team with
more than one consumer of an API benefits from this, regardless of
language stack. Land on the last, highlighted row — that's the whole
talk in one sentence.
-->

---
layout: end
class: 'text-center bg-neutral-950'
---

<h1 class="text-5xl font-bold mb-6" style="color: #fff;">Thanks!</h1>

<div class="flex items-center justify-center gap-2 text-neutral-300 mb-6">
<svg class="w-5 h-5" viewBox="0 0 24 24" fill="currentColor"><path d="M12 .5C5.65.5.5 5.65.5 12c0 5.08 3.29 9.39 7.86 10.91.57.1.78-.25.78-.55 0-.27-.01-1.17-.02-2.12-3.2.7-3.88-1.36-3.88-1.36-.52-1.34-1.28-1.7-1.28-1.7-1.05-.72.08-.7.08-.7 1.16.08 1.77 1.19 1.77 1.19 1.03 1.77 2.7 1.26 3.36.96.1-.75.4-1.26.73-1.55-2.55-.29-5.24-1.28-5.24-5.7 0-1.26.45-2.29 1.19-3.09-.12-.29-.52-1.46.11-3.05 0 0 .97-.31 3.18 1.18.92-.26 1.91-.38 2.89-.39.98.01 1.97.13 2.89.39 2.2-1.49 3.17-1.18 3.17-1.18.63 1.59.23 2.76.11 3.05.74.8 1.19 1.83 1.19 3.09 0 4.43-2.69 5.41-5.25 5.69.41.36.78 1.06.78 2.14 0 1.55-.01 2.79-.01 3.17 0 .3.2.66.79.55A10.52 10.52 0 0 0 23.5 12C23.5 5.65 18.35.5 12 .5z"/></svg>
<span class="font-mono">github.com/TechieRpk/javazone-2026</span>
</div>

<div class="flex justify-center">
<img src="/images/repo-qrcode.png" class="w-40 h-40 rounded-lg bg-white p-2" />
</div>

<!--
Point at the URL and QR code, invite questions, stop talking.
-->

