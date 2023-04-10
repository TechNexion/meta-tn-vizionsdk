SUMMARY = "TechNexion VizionSDK"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

SRCSERVER = "git://github.com/TechNexion-Vision/vizionviewer.git;protocol=https"
SRCOPTIONS = ""
SRCBRANCH = "main"
SRC_URI = "${SRCSERVER};branch=${SRCBRANCH};${SRCOPTIONS}"
#SRCREV = "${AUTOREV}"
SRCREV = "417a85330fd0f37515375fd7a2029d4a0c44fd58"
PV = "23.04.1"

DEPENDS += "libdrm"
RDEPENDS:${PN} += "libdrm-dev"

S = "${WORKDIR}/git/vizionsdk"

INSANE_SKIP:${PN} += "dev-deps"

do_install() {
	mv ${S}/libVizionSDK.so libVizionSDK.so.${PV}

	install -d ${D}${libdir}
	install -m 0644 ${S}/libVizionSDK.so.${PV} ${D}${libdir}
	ln -snf libVizionSDK.so.${PV} ${D}${libdir}/libVizionSDK.so
}
