SUMMARY = "TechNexion VizionViewer"

require recipes-vizionsdk/vizionsdk.inc

DEPENDS = "vizionsdk"
RDEPENDS:${PN} += "bash vizionsdk libjpeg-turbo"

S = "${UNPACKDIR}"

FILES:${PN} = "/opt/* \
               ${datadir}/applications/* \
               ${bindir}/vizionviewer \
"

INSANE_SKIP:${PN} += "already-stripped ldflags file-rdeps dev-so libdir"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

# Prevent automatic shlib dependencies
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_install() {
    cd ${UNPACKDIR}
    # Extract the .deb using ar
    ar x vizionviewer.deb
    # Extract data.tar.* without preserving ownership
    tar --no-same-owner -xf data.tar.* -C ${D}
    rm -f control.tar.* data.tar.* debian-binary

    # Create symbolink of vizionviewer.sh
    install -d ${D}${bindir}
    ln -sf /opt/vizionviewer/vizionviewer.sh ${D}${bindir}/vizionviewer
}
