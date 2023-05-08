SUMMARY = "TechNexion apt mirror list"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

SRC_URI = "file://technexion.gpg file://vizionsdk.list"

RDEPENDS:${PN} += "${@bb.utils.contains_any('UBUNTU_TARGET_ARCH', 'arm64 arm', '', 'gnupg', d)}"

S = "${WORKDIR}"

do_install() {
	# Install vizionsdk.list
	install -D -t ${D}${sysconfdir}/apt/sources.list.d -m 0644 ${S}/vizionsdk.list

	# Install technexion.gpg
	install -D -t ${D}${sysconfdir}/apt/trusted.gpg.d -m 0644 ${S}/technexion.gpg
}
