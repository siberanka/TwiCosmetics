# Changelog

## 3.16.0-twi.2 - 2026-07-20

- Merged all 16 upstream commits through UltraCosmetics 3.16 while preserving TwiCosmetics package, metadata, compatibility aliases, and fork hardening.
- Replaced polling-based flat-file profile shutdown with a deterministic, zero-spin drain barrier and added concurrency regression tests.
- Prevented temporary-file collisions during concurrent atomic YAML saves.
- Integrated upstream rocket safety, faster snake movement, debug, translation, dependency, and workflow improvements.
- Guarded rocket dismount cleanup against a null active task.

## 3.16.0-twi.1 - 2026-07-12

- Forked and rebranded the plugin as TwiCosmetics under `com.siberanka.twicosmetics`.
- Added `siberanka` as an author while preserving legacy commands and permission nodes.
- Added a non-destructive first-start migration for existing `plugins/UltraCosmetics` configuration and player data.
- Hardened inventory menus, purchase confirmation, economy transactions, treasure keys, and structure reservations against replay and duplication bugs.
- Reworked player, entity, particle, vanish, damage, and database scheduling for Folia-safe ownership and lower server load.
- Added coalesced profile saves, bounded shutdown flushing, atomic configuration writes, SQL table-name validation, command throttling, and particle limits.
- Secured updates to verified GitHub release assets with size, timeout, and SHA-256 checks.
- Added concurrency and validation tests plus Gradle dependency locks and checksum verification metadata.
- Resolved the source TODO items and documented the remaining feature wishlist in `ROADMAP.md`.
