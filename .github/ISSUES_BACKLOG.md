# CrossRoads Enhancement Issue Backlog

This document captures the proposed feature additions as GitHub-ready issues.

> Apply these labels to each issue: `enhancement`, `ready`

## 1) Add ICU-style pluralization and select support

- **Title:** Add ICU message format backend for plural/select localization
- **Labels:** `enhancement`, `ready`
- **Problem:** `MessageFormat` supports parameter formatting but is awkward for pluralization and selection logic.
- **Proposed change:** Introduce an optional ICU-based formatter backend while keeping the existing `MessageFormat` path as default for backward compatibility.
- **Acceptance criteria:**
  - API supports plural/select syntax for localization messages.
  - Existing `MessageFormat` behavior remains unchanged unless ICU mode is enabled.
  - Unit tests cover pluralization for at least 2 locales.

## 2) Add configurable missing-key behavior

- **Title:** Support configurable missing-key policies in I18n lookup
- **Labels:** `enhancement`, `ready`
- **Problem:** Missing keys currently fall back to returning the key and logging a warning.
- **Proposed change:** Add configurable policies: return key (default), return caller-supplied fallback text, throw exception, and callback hook for missing-key collection.
- **Acceptance criteria:**
  - Configuration option exists for selecting missing-key behavior.
  - Default behavior remains backward compatible.
  - Tests validate all policy modes.

## 3) Add refreshable filesystem bundle loading

- **Title:** Add hot-reload/refresh strategy for filesystem resource bundles
- **Labels:** `enhancement`, `ready`
- **Problem:** Filesystem-backed bundles cannot be refreshed without restart.
- **Proposed change:** Add reload support via explicit refresh API and/or TTL cache invalidation strategy.
- **Acceptance criteria:**
  - Filesystem mode can pick up changed bundle files without process restart.
  - Thread-safe cache refresh behavior is documented and tested.
  - Reload can be disabled for current static behavior.

## 4) Add UTF-8 properties bundle loading

- **Title:** Add UTF-8 ResourceBundle loading support
- **Labels:** `enhancement`, `ready`
- **Problem:** Java `.properties` loading can be constrained by legacy encoding expectations.
- **Proposed change:** Provide UTF-8 loading via custom `ResourceBundle.Control` (or equivalent strategy).
- **Acceptance criteria:**
  - UTF-8 bundles load correctly without unicode escaping.
  - Existing encoded bundle behavior remains supported.
  - Tests include non-ASCII characters across locales.

## 5) Introduce non-singleton I18n client API

- **Title:** Add instance-based I18n API while preserving static singleton API
- **Labels:** `enhancement`, `ready`
- **Problem:** Global singleton configuration limits multi-tenant and test isolation use cases.
- **Proposed change:** Add an instance-based client that accepts its own config and bundle sources.
- **Acceptance criteria:**
  - Multiple independent I18n clients can coexist in one JVM.
  - Existing static API remains functional.
  - Tests verify isolation between two differently configured clients.

## 6) Add named parameter interpolation

- **Title:** Support named placeholders (e.g., `{username}`) in localized messages
- **Labels:** `enhancement`, `ready`
- **Problem:** Positional arguments (`{0}`, `{1}`) are less readable and easier to misuse.
- **Proposed change:** Add named-parameter interpolation API, mapping names to values before formatting.
- **Acceptance criteria:**
  - API accepts a map/object of named values.
  - Missing named params produce deterministic behavior (configurable if possible).
  - Tests cover mixed numeric/date/currency named params.

## 7) Add bundle validation utility for CI

- **Title:** Add bundle consistency validator for missing/extra keys and placeholder mismatches
- **Labels:** `enhancement`, `ready`
- **Problem:** Translation quality regressions are hard to detect before runtime.
- **Proposed change:** Add a validation utility (CLI or test helper) to compare locale bundles.
- **Acceptance criteria:**
  - Reports missing keys per locale.
  - Reports extra keys per locale.
  - Detects placeholder schema mismatches.

## 8) Add configurable locale fallback chains

- **Title:** Add configurable locale fallback strategy (e.g., `fr-CA -> fr -> en`)
- **Labels:** `enhancement`, `ready`
- **Problem:** Fallback behavior is not explicitly configurable for partial localization sets.
- **Proposed change:** Add fallback chain configuration with sensible defaults.
- **Acceptance criteria:**
  - Fallback chain can be configured per app.
  - Default chain remains backward compatible.
  - Tests verify fallback precedence in multiple scenarios.

## 9) Add localization introspection APIs

- **Title:** Add `hasKey(...)` and `getAvailableLocales()` introspection APIs
- **Labels:** `enhancement`, `ready`
- **Problem:** Callers need non-exception/non-formatting checks for message presence and locale support.
- **Proposed change:** Expose lightweight introspection methods on the I18n API.
- **Acceptance criteria:**
  - API can check key existence by locale.
  - API can enumerate available locales for configured bundles.
  - Tests validate both classpath and filesystem-backed bundles.

## 10) Expose cache/reload concurrency controls

- **Title:** Add explicit cache policy and concurrency controls for bundle refresh
- **Labels:** `enhancement`, `ready`
- **Problem:** Reload support requires clear concurrency and caching semantics in server environments.
- **Proposed change:** Add cache policy options and document thread-safety guarantees.
- **Acceptance criteria:**
  - Cache strategy is configurable (e.g., static, TTL, manual refresh).
  - Behavior under concurrent lookups and refresh is tested.
  - Public docs describe consistency model.

## 11) Improve Accept-Language parsing in RestExpress plugin

- **Title:** Parse `Accept-Language` with q-values and best-match resolution
- **Labels:** `enhancement`, `ready`
- **Problem:** Direct locale construction from the raw header does not honor weighted language preferences.
- **Proposed change:** Parse header entries with q-values and select highest-ranked supported locale.
- **Acceptance criteria:**
  - Header parsing supports complex values like `en-US,en;q=0.9,fr;q=0.8`.
  - Best-match locale is selected deterministically.
  - Unit tests cover weighted and malformed header inputs.

## 12) Add supported locale whitelist for RestExpress plugin

- **Title:** Support configured locale whitelist and fallback in RestExpress plugin
- **Labels:** `enhancement`, `ready`
- **Problem:** Plugin can propagate locales not supported by available bundles.
- **Proposed change:** Allow registration of supported locales and fallback resolution strategy.
- **Acceptance criteria:**
  - Supported locales can be configured.
  - Unsupported client locales resolve to configured default/fallback.
  - Tests validate whitelist matching behavior.

## 13) Add RestExpress locale attachment helper API

- **Title:** Add helper utility for retrieving request locale attachment safely
- **Labels:** `enhancement`, `ready`
- **Problem:** Consumers currently rely on raw attachment keys.
- **Proposed change:** Add a small helper API to set/get locale attachment consistently.
- **Acceptance criteria:**
  - Helper exposes typed accessor methods.
  - Raw key usage is minimized in plugin examples/docs.
  - Tests verify helper compatibility with plugin behavior.

## 14) Add optional framework adapters beyond RestExpress

- **Title:** Add optional adapters/integrations for additional Java web frameworks
- **Labels:** `enhancement`, `ready`
- **Problem:** Current framework integration is limited to RestExpress.
- **Proposed change:** Provide optional modules/helpers for common frameworks (e.g., Spring/JAX-RS).
- **Acceptance criteria:**
  - At least one additional framework adapter is implemented.
  - Adapter preserves CrossRoads locale and message lookup semantics.
  - Integration tests/documentation demonstrate usage.

## 15) Add localization diagnostics/metrics mode

- **Title:** Add diagnostics mode for missing keys, fallback hits, and formatting errors
- **Labels:** `enhancement`, `ready`
- **Problem:** Production visibility into localization quality is limited.
- **Proposed change:** Add optional diagnostics hooks/log events/counters.
- **Acceptance criteria:**
  - Missing key and fallback events are observable.
  - Formatting errors can be tracked.
  - Diagnostics can be enabled/disabled with minimal overhead.
