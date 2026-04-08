# EvalRM - KMP Rick and Morty Locations

Application Kotlin Multiplatform (Android + Desktop) basee sur les endpoints `location` de Rick and Morty API.

Objectif fonctionnel:
- afficher un listing de locations
- ouvrir le detail d'une location
- mobile: navigation liste -> detail
- desktop: ecran unique master-detail (liste gauche, detail droite)

## Stack technique

- Kotlin Multiplatform
- Compose Multiplatform
- Ktor Client (remote source)
- kotlinx.serialization (parsing JSON)
- Coroutines + StateFlow
- Koin (injection de dependances)

## Architecture (clean, par couches)

Code partage dans `composeApp/src/commonMain/kotlin/com/example/evalrm`:

- `domain/`
  - `model/Location.kt` modele metier
  - `repository/LocationRepository.kt` contrat metier
  - `usecase/` use cases de lecture liste/detail
- `data/`
  - `remote/` source distante Rick and Morty
  - `local/` source locale en cache memoire
  - `mapper/` mapping remote -> domain
  - `repository/LocationRepositoryImpl.kt` implementation du contrat Domain
- `presentation/`
  - `list/` contract, viewmodel, ecran liste
  - `detail/` contract, viewmodel, ecran detail
  - `navigation/` navigation mobile centralisee
  - `designsystem/` tokens de couleurs, typographie, shapes et theme
  - `AppRoot.kt` orchestration UI mobile/desktop
- `cross/audio/`
  - contrat audio `AudioManager`
  - `expect fun rememberAudioManager()`

Code specifique plateforme:

- `androidMain/`
  - `cross/audio/AudioManager.android.kt`
  - `cross/context/ContextExtensions.kt` (extension de `Context`)
- `jvmMain/`
  - `cross/audio/AudioManager.jvm.kt`
  - `main.kt` (desktop mode master-detail)

## UDF / MVI applique

Chaque ecran suit un contrat explicite:
- `UiState` = ce que l'UI rend
- `Intent` = intentions utilisateur/systeme
- `ViewModel` = orchestration des actions, appels use cases, mise a jour du `StateFlow`

Cette separation permet:
- un rendu Compose simple (Screen)
- une logique testable hors composables (ViewModel)
- une lecture claire du flux d'etat

## Design System

Le Design System est centralise dans `presentation/designsystem/`:
- `ColorTokens.kt` pour les couleurs applicatives
- `TypographyTokens.kt` pour la typographie
- `ShapeTokens.kt` pour les formes
- `EvalRmTheme.kt` pour appliquer ces tokens a toute l'UI

`AppRoot.kt` applique `EvalRmTheme`, ce qui garantit une coherence visuelle sur Android et Desktop.

## Strategie Data (2 sources + fetch)

- Source 1: remote API (`RickAndMortyRemoteDataSource`)
- Source 2: cache local memoire (`LocationLocalDataSource`)

Fetch policy:
1. lire le cache si disponible et `forceRefresh == false`
2. sinon appeler l'API distante
3. mapper en modele metier
4. mettre a jour le cache

Cette strategie est volontairement simple et defendable dans le temps imparti.

## Cross-Native

- `expect/actual` utilise pour l'audio:
  - Android: `MediaActionSound`
  - Desktop: `Toolkit.beep()`
- extension Android `Context.audioManager()` pour construire le manager audio natif
- l'audio est declenche lors de l'ouverture d'un detail (usage reel)

## Injection de dependances

Koin est utilise pour assembler le graphe applicatif:
- module commun: `core/di/AppModules.kt`
- bootstrap commun: `core/di/KoinBootstrap.kt`
- init Android: `androidMain/kotlin/com/example/evalrm/EvalRmApplication.kt`
- init Desktop: `jvmMain/kotlin/com/example/evalrm/main.kt`

La Presentation recupere les use cases via Koin puis les injecte dans les ViewModels.

## Lancer le projet (Windows)

```powershell
.\gradlew.bat :composeApp:run
.\gradlew.bat :composeApp:assembleDebug
```

## Lancer les tests

```powershell
.\gradlew.bat :composeApp:allTests
```
