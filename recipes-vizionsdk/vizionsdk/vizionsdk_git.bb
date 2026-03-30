SUMMARY = "TechNexion VizionSDK"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

SRCSERVER = "git://github.com/technexion/tn-vizionsdk.git;protocol=https"
SRCOPTIONS = ""
SRCBRANCH = "master"
SRC_URI = "${SRCSERVER};branch=${SRCBRANCH};${SRCOPTIONS}"
SRCREV = "${AUTOREV}"

#DEPENDS += "qtbase"
RDEPENDS:${PN} += "qtbase"
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
	mv ${S}/libVizionSDK.so libVizionSDK.so.${PV}

	install -d ${D}${libdir}
	install -m 0644 ${S}/libVizionSDK.so.${PV} ${D}${libdir}
	ln -snf libVizionSDK.so.${PV} ${D}${libdir}/libVizionSDK.so
}
