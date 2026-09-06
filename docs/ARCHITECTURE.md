# Architecture

```mermaid
flowchart LR
  R[Fire TV remote / voice command] --> A[Android TV shell]
  A --> W[Bundled WebView UI]
  W --> C[Local canvas engine]
  C --> P[(LocalStorage)]
  C --> T[Templates + auto-connectors]
  W --> B[Split-screen browser]
  W -->|explicit confirmation; one compact batch| S[Optional Express proxy]
  S -->|Responses API| O[OpenAI]
  O -->|schema-constrained cards + usage| S
  S --> W
```

## Boundaries

The Android shell is deliberately thin: fullscreen lifecycle, WebView configuration, and remote key translation. All primary creation logic ships in the APK and remains usable offline. Canvas state never leaves the device during ordinary use.

The proxy exists only to prevent API-key exposure and validate/cap requests. It stores nothing. A single organization event serializes only card titles and bodies—not positions, browsing history, or unrelated local state—and sends one Responses API request. The UI makes this boundary visible before and after the call.

## Collaboration path

This MVP provides living-room collaboration through a shared screen and remote handoff. A later LAN companion client can synchronize operations through a local WebSocket/CRDT process without changing the cloud boundary: card operations remain local-network events; only explicit high-value actions reach the proxy.

## Browser constraints

Some websites block iframe embedding. A production build should replace the iframe pane with a second Android WebView and an allowlist, navigation controls, certificate handling, and parental/privacy controls.
