require recipes-vizionsdk/vizionsdk.inc

SRC_URI += "file://88-cyusb.rules"

RDEPENDS:${PN} = "libusb1 udev bash"

S = "${UNPACKDIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Main package: runtime libraries, udev rules, and data files
FILES:${PN} = "${libdir}/libVizionSDK.so.* \
               ${sysconfdir}/udev/rules.d/88-cyusb.rules \
               ${datadir}/vizionsdk/* \
               ${bindir}/vizion-ctl \
"

# Development package (CMake files, headers, .so symlinks)
FILES:${PN}-dev = "${includedir}/* \
                   ${libdir}/libVizionSDK.so \
                   ${libdir}/cmake/vizionsdk/* \
                   ${libdir}/pkgconfig/* \
"

INSANE_SKIP:${PN} += "already-stripped ldflags file-rdeps dev-so"
INSANE_SKIP:${PN}-dev += "dev-elf"

# Don't try to strip or create debug packages
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

do_install() {
	# Install configurations of Cypress USB
	install -D -t ${D}${sysconfdir}/udev/rules.d -m 0644 ${UNPACKDIR}/88-cyusb.rules

    cd ${UNPACKDIR}
    # Extract the .deb using ar
    ar x vizionsdk.deb
    # Extract data.tar.* without preserving ownership
    tar --no-same-owner -xf data.tar.* -C ${D}
    rm -f control.tar.* data.tar.* debian-binary
}
