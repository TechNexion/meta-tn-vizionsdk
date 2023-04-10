SUMMARY = "TechNexion VizionViewer"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

SRCSERVER = "git://github.com/TechNexion-Vision/vizionviewer.git;protocol=https"
SRCOPTIONS = ""
SRCBRANCH = "main"
SRC_URI = "${SRCSERVER};branch=${SRCBRANCH};${SRCOPTIONS}"
#SRCREV = "${AUTOREV}"
SRCREV = "417a85330fd0f37515375fd7a2029d4a0c44fd58"
PV = "23.04.1"

DEPENDS += "qtbase qtmultimedia qtdeclarative vizionsdk"
RDEPENDS:${PN} += "qtbase qtmultimedia qtdeclarative vizionsdk-dev"

S = "${WORKDIR}/git/vizionviewer"

INSANE_SKIP:${PN} += "dev-deps"

do_install() {
	if test -f ${S}/vizionviewer.desktop; then
		install -d ${D}${datadir}/applications
		install -m 0644 ${WORKDIR}/vizionviewer.desktop ${D}${datadir}/applications/
	fi

	install -d ${D}${bindir}
	install -m 0755 ${S}/VizionViewer ${D}${bindir}
}
