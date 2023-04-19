SUMMARY = "TechNexion apt mirror list"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

SRC_URI = "file://technexion.gpg file://vizionsdk.list"

S = "${WORKDIR}"

do_install() {
	# Install vizionsdk.list
	#_d="${D}${sysconfdir}/apt/sources.list.d"
	#install -d "${_d}"
	install -D -t ${D}${sysconfdir}/apt/sources.list.d -m 0644 ${S}/vizionsdk.list

	# Install technexion.gpg
	#_d="${D}${sysconfdir}/apt/trusted.gpg.d"
	#install -d "${_d}"
	install -D -t ${D}${sysconfdir}/apt/trusted.gpg.d -m 0644 ${S}/technexion.gpg
}
