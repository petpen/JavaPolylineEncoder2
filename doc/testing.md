Testing
===

JUnit
---

If you want to start the [JUnit](http://www.junit.org/) tests you have to tell ant to use the check branch.
He will also be selected if no parameter is given.

    ant check

The test reports can be found in `reports` the cobertura report is stored in `cob-rep`

Testfiles
---

Various testfiles can be found inside the folder `testfiles` and its subdirectories.
The file `testfiles/readFile.test` is only needed for junit tests.