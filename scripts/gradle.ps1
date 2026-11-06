param([Parameter(ValueFromRemainingArguments = $true)][string[]]$GradleArgs)
$ErrorActionPreference = 'Stop'
$addonRoot = Split-Path -Parent $PSScriptRoot
$localJdk = Get-ChildItem -LiteralPath (Join-Path $addonRoot '.tools') -Directory -Filter 'jdk-17*' -ErrorAction SilentlyContinue | Select-Object -First 1
if ($localJdk) { $env:JAVA_HOME = $localJdk.FullName }
$gameJdk = Get-ChildItem -LiteralPath (Join-Path $addonRoot '.tools') -Directory -Filter 'jdk8*' -ErrorAction SilentlyContinue | Select-Object -First 1
if ($gameJdk) { $env:SOULCATCHER_GAME_JAVA = $gameJdk.FullName }
if (-not $env:JAVA_HOME) { throw 'Set JAVA_HOME to Java 17, or run python scripts/bootstrap.py in this repository.' }
if (-not $env:GRADLE_USER_HOME) { $env:GRADLE_USER_HOME = Join-Path $addonRoot '.gradle-user-home' }
& (Join-Path $addonRoot 'gradlew.bat') -p $addonRoot @GradleArgs
exit $LASTEXITCODE
