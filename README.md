# Open Canvas for Fire TV

A TV-first collaborative thinking surface—not a streaming app. Canvas creation, templates, card movement, connectors, navigation, persistence, and browser mode all run locally. The OpenAI API is used only after explicit confirmation for a high-value canvas transformation.

## Product principles

- **Zero-call core:** mind maps, timelines, flowcharts, card editing, connectors, zoom, pan, undo, and persistence need no service.
- **TV first:** 10-foot typography, high contrast, D-pad movement, obvious focus states, sparse controls.
- **Explicit intelligence:** the only network AI affordance is labeled `Organize with AI`, previews payload size, and requires confirmation.
- **One batch:** all cards are compacted into one request. The API key remains on the optional server.
- **Graceful demo:** when the backend is absent, a clearly labeled deterministic demo transformation keeps the interaction testable; it does not claim to be AI.

## Run the Android app

Requirements: JDK 17, Android SDK 35, and Gradle 8.9+ (or create a wrapper with `gradle wrapper --gradle-version 8.9`).

```bash
gradle :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

The app is also mouse/keyboard-testable: arrow keys move the selected card, Enter selects/starts, Escape goes back. On a Fire TV remote, D-pad and Select map directly; Menu opens templates and Play/Pause surfaces voice-command help.

## Enable live AI organization

```bash
cd server
npm install
OPENAI_API_KEY=your_key npm start
```

The emulator uses `http://10.0.2.2:8787`. For physical Fire TV hardware, change `state.api` in `app/src/main/assets/app.js` to the LAN address of the server and keep the key off-device. The server validates payloads, caps the canvas at 60 cards/64 KB, requests schema-constrained JSON, and returns usage metadata for the on-screen receipt.

## Controls

| Action | Fire TV remote | Keyboard |
|---|---|---|
| Start default mind map | Select | Enter |
| Move focused card | D-pad | Arrow keys |
| Open templates | Menu | Click Templates |
| Close overlay/browser | Back | Escape |
| Voice help | Play/Pause | Click/remote |

Voice is intentionally command-only (`zoom in`, `pan right`, `switch view`). Production voice input should use the Fire TV voice-search intent or a companion remote; it must dispatch these same local commands and never call an AI service.

## Repository

```text
app/      Android TV shell and bundled offline canvas
server/   Optional, stateless OpenAI key proxy
docs/     Architecture, demo, and product decisions
scripts/  Lightweight source verification
```

Licensed under MIT. See [LICENSE](LICENSE).

# FireStick
