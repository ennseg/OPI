# ---------------------------------------------------------------------------
# Launch VisualVM with WildFly jboss-cli-client.jar on the classpath
# Then: File -> Add JMX Connection -> service:jmx:remote+http://localhost:9990
# Credentials: admin / Admin#1234
# ---------------------------------------------------------------------------

$JBOSS_CLIENT = "C:\wildfly-40.0.0.Final\bin\client\jboss-cli-client.jar"
$JAVA_HOME     = "C:\Program Files\Java\jdk-17"

# Try common VisualVM install locations
$candidates = @(
    "C:\visualvm_221\bin\visualvm.exe",
    "$env:ProgramFiles\VisualVM\bin\visualvm.exe",
    "$env:ProgramFiles\visualvm\bin\visualvm.exe",
    "C:\visualvm\bin\visualvm.exe",
    "C:\Program Files\Java\jdk-17\bin\jvisualvm.exe",
    "C:\Program Files\Java\jdk-22\bin\jvisualvm.exe"
)

$visualvmExe = $null
foreach ($path in $candidates) {
    if (Test-Path $path) { $visualvmExe = $path; break }
}

if (-not $visualvmExe) {
    Write-Host "VisualVM not found at known locations."
    Write-Host "If you have VisualVM installed, run it manually and then:"
    Write-Host "  1. Tools -> Options -> JMX -> add jboss-cli-client.jar to 'JMX Connections' classpath"
    Write-Host "  OR start VisualVM with:"
    Write-Host "  visualvm --cp:a `"$JBOSS_CLIENT`""
    Write-Host ""
    Write-Host "Then connect via: File -> Add JMX Connection"
    Write-Host "  URL:  service:jmx:remote+http://localhost:9990"
    Write-Host "  User: admin    Password: Admin#1234"
    exit 1
}

Write-Host "Found VisualVM: $visualvmExe"
Write-Host "Adding jboss-cli-client.jar to classpath..."
Write-Host ""
Write-Host "After VisualVM opens:"
Write-Host "  File -> Add JMX Connection"
Write-Host "  URL:  service:jmx:remote+http://localhost:9990"
Write-Host "  User: admin    Password: Admin#1234"
Write-Host ""

Start-Process -FilePath $visualvmExe -ArgumentList "--cp:a `"$JBOSS_CLIENT`""