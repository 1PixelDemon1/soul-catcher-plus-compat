param([Parameter(ValueFromRemainingArguments = $true)][string[]]$GradleArgs)
$ErrorActionPreference = 'Stop'
$addonRoot = Split-Path -Parent $PSScriptRoot
$coreRoot = Join-Path (Split-Path -Parent $addonRoot) 'SoulCatcherPlus'
if (Test-Path -LiteralPath (Join-Path $addonRoot '..\..\gradle.properties')) {
    $coreRoot = Join-Path (Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $addonRoot))) 'SoulCatcherPlus'
}
$localJdk = Get-ChildItem -LiteralPath (Join-Path $coreRoot '.tools') -Directory -Filter 'jdk-17*' -ErrorAction SilentlyContinue | Select-Object -First 1
if ($localJdk) { $env:JAVA_HOME = $localJdk.FullName }
if (-not $env:JAVA_HOME) { throw 'Set JAVA_HOME to Java 17 or bootstrap the adjacent SoulCatcherPlus checkout.' }
$env:GRADLE_USER_HOME = Join-Path $coreRoot '.gradle-user-home'
& (Join-Path $addonRoot 'gradlew.bat') -p $addonRoot @GradleArgs
exit $LASTEXITCODE
