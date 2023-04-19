SUMMARY = "TechNexion VizionViewer"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

include recipes-vizionsdk/vizionsdk_git.inc

PV = "23.04.2"

DEPENDS += "vizionsdk"
RDEPENDS:${PN} += "vizionsdk-dev"
DEPENDS += "${@bb.utils.contains_any('UBUNTU_TARGET_ARCH', 'arm64 arm', '', 'qtbase qtmultimedia qtdeclarative libjpeg-turbo', d)}"
RDEPENDS:${PN} += "${@bb.utils.contains_any('UBUNTU_TARGET_ARCH', 'arm64 arm', '', 'qtbase qtmultimedia qtdeclarative libturbojpeg libxkbcommon', d)}"

S = "${WORKDIR}/git/vizionviewer"

INSANE_SKIP:${PN} += "dev-deps file-rdeps already-stripped"
FILES:${PN} += "/opt/* /usr/share/polkit-1/actions/*"

EXCLUDE_FROM_SHLIBS = "1"

do_install() {
	_opt_d="/opt/vizionviewer"
	# Install VizionViewer and related Qt libraries
	cp -r ${S}/opt ${D}
	chmod 755 ${D}${_opt_d}/vizionviewer.sh

	# Create symbolink of vizionviewer.sh
	install -d ${D}${bindir}
	ln -snf ${_opt_d}/vizionviewer.sh ${D}${bindir}/vizionviewer

	# Install application icon
	install -D -t ${D}${datadir}/applications -m 0755 ${S}/usr/share/applications/*
	install -D -t ${D}${datadir}/polkit-1/actions -m 0644 ${S}/usr/share/polkit-1/actions/*
}
