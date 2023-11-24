SRCSERVER = "git://github.com/TechNexion-Vision/vizionviewer.git;protocol=https"
SRCOPTIONS = ""
SRCBRANCH = "main"
SRC_URI = "${SRCSERVER};branch=${SRCBRANCH};${SRCOPTIONS}"
SRCREV = "98b878a5753827cb7a47e4d7a96bdb136f7babb5"

python do_package:prepend() {
    # Get package version from debian package control file
    f = open("{}/DEBIAN/control".format(d.getVar("S")))
    for line in f.readlines():
        if not "Version:" in line:
            continue
        pkgv = line.split(":")[1].strip()
        break;
    f.close
    d.setVar('PKGV', pkgv)
}
