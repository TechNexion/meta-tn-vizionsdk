require recipes-vizionsdk/vizionsdk.inc

SRC_URI += "file://88-cyusb.rules"

RDEPENDS:${PN} = "libusb1 udev bash"

S = "${UNPACKDIR}"

COMPATIBLE_HOST = "aarch64.*-linux"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Keep the upstream Debian package monolithic. The TechNexion-hosted
# vizionsdk .deb owns headers in the main package, so splitting these files into
# vizionsdk-dev in the Yocto image makes local apt upgrades fail on overwrite.
PACKAGES = "${PN}"

# Main package: runtime libraries, development files, udev rules, and data files
FILES:${PN} = "${libdir}/libVizionSDK.so.* \
               ${libdir}/libVizionSDK.so \
               ${libdir}/cmake/vizionsdk/* \
               ${libdir}/pkgconfig/* \
               ${includedir}/* \
               ${sysconfdir}/cyusb.conf \
               ${sysconfdir}/udev/rules.d/88-cyusb.rules \
               ${datadir}/vizionsdk/* \
               ${bindir}/cy_renumerate.sh \
               ${bindir}/vizion-ctl \
"

INSANE_SKIP:${PN} += "already-stripped ldflags file-rdeps dev-so"

# Don't try to strip or create debug packages
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    cd ${UNPACKDIR}
    # Extract the .deb using ar
    ar x vizionsdk.deb
    # Extract data.tar.* without preserving ownership
    tar --no-same-owner -xf data.tar.* -C ${D}
    rm -f control.tar.* data.tar.* debian-binary

    # Install configurations of Cypress USB
    install -D -t ${D}${sysconfdir}/udev/rules.d -m 0644 ${UNPACKDIR}/88-cyusb.rules
    install -D -t ${D}${sysconfdir} -m 0644 ${D}${datadir}/vizionsdk/driver/cyusb.conf
    install -D -t ${D}${bindir} -m 0755 ${D}${datadir}/vizionsdk/driver/cy_renumerate.sh
}
