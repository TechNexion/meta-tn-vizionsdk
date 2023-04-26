SUMMARY = "TechNexion VizionSDK"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

include recipes-vizionsdk/vizionsdk_git.inc

PV = "23.04.1"

SRC_URI += "file://88-cyusb.rules"

DEPENDS += "${@bb.utils.contains_any('UBUNTU_TARGET_ARCH', 'arm64 arm', '', 'libdrm libusb1 bash', d)}"
RDEPENDS:${PN} += "libdrm-dev tn-apt-list"
RDEPENDS:${PN} += "${@bb.utils.contains_any('UBUNTU_TARGET_ARCH', 'arm64 arm', '', 'libdrm-dev libusb1 bash', d)}"

S = "${WORKDIR}/git/vizionsdk/usr"

INSANE_SKIP:${PN} += "${@bb.utils.contains_any('UBUNTU_TARGET_ARCH', 'arm64 arm', 'file-rdeps', '', d)}"
INSANE_SKIP:${PN} += "dev-deps"
FILES:${PN} += "/usr/local/bin/*"

# keep package name as vizionsdk
DEBIAN_NOAUTONAME:${PN} := "1"
DEBIAN_NOAUTONAME:${PN}-dev := "1"
DEBIAN_NOAUTONAME:${PN}-dbg := "1"

do_install() {
	_lib_n="libVizionSDK.so"
	# Find the real libVizionSDK.so
	_lst=$(ls ${S}/lib/${_lib_n}* | xargs)
	for _l in ${_lst}; do
		test -L ${_l} && continue
		_lib=$(basename ${_l})
	done

	# Install libVizionSDK.so
	if test ! -z ${_lib}; then
		install -D -t ${D}${libdir} -m 0644 ${S}/lib/${_lib}
		# get version
		_lib_v=$(echo ${_lib} | cut -f3- -d'.')
		# combine majro version
		_so="${_lib_n}.$(echo ${_lib_v} | cut -f1 -d'.')"
		#_so="$(echo ${_lib_v} | awk -F'.' '{ print $_lib_n"."$1 }')"
		ln -snf ${_lib_n}.${_lib_v} ${D}${libdir}/${_so}
		ln -snf ${_so} ${D}${libdir}/${_lib_n}
	fi

	# Install configurations of Cypress USB
	install -D -t ${D}${sysconfdir}/udev/rules.d -m 0644 ${WORKDIR}/88-cyusb.rules
	install -D -t ${D}${sysconfdir} -m 0644 ${S}/share/vizionsdk/driver/cyusb.conf
	install -D -t ${D}/usr/local/bin -m 0755 ${S}/share/vizionsdk/driver/cy_renumerate.sh
}
