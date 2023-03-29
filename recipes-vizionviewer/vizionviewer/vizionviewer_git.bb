SUMMARY = "TechNexion VizionViewer"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

SRCSERVER = "git://github.com/technexion/tn-vizionviewer.git;protocol=https"
SRCOPTIONS = ""
SRCBRANCH = "master"
SRC_URI = "${SRCSERVER};branch=${SRCBRANCH};${SRCOPTIONS}"
SRCREV = "${AUTOREV}"

#DEPENDS += "qtbase"
RDEPENDS:${PN} += "qtbase vizionsdk"
#BBCLASSEXTEND = "native nativesdk"

#PACKAGECONFIG ??= " \
#    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)} \
#    ${@bb.utils.contains('DISTRO_FEATURES', 'weston-xwayland', 'weston-xwayland', '', d)} \
#"
#PACKAGECONFIG[wayland] = "-Dwayland=enabled,-Dwayland=disabled,wayland-native wayland wayland-protocols libdrm"
#PACKAGECONFIG[x11] = "-Dx11=enabled,-Dx11=disabled,libxcb libxkbcommon"

PV = "git${SRCPV}"
S = "${WORKDIR}/git"

do_install() {
	if test -f ${S}/vizionviewer.desktop; then
		install -d ${D}${datadir}/applications
		install -m 0644 ${WORKDIR}/vizionviewer.desktop ${D}${datadir}/applications/
	fi

	install -d ${D}${bindir}
	install -m 0644 ${S}/VizionViewer ${D}${bindir}
}
