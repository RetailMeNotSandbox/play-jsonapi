Publishing locally
----------
For testing locally

    sbt publish-local

Publishing internally
----------
Credentials should be available at ~/.ivy2/.credentials and are available on LastPass

    realm=Sonatype Nexus Repository Manager
    host=maven.pkg.rmn.io
    user=***
    password=***

With the above credentials stored:

    sbt pushlish
