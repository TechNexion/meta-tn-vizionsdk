SUMMARY = "TechNexion PyVizionSDK"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

inherit python3native

MANYLINUX_VER = "2_31"
PYVIZIONSDK_VER = "25.9.3"

do_install[network] = "1"

DEPENDS = "python3-pip-native"
RDEPENDS:${PN} = "\
    python3-core \
    python3-numpy \
    python3-curses \
    python3-six \
    python3-wcwidth \
    python3-editor \
    python3-terminal \
    python3-fcntl \
    python3-io \
    python3-logging \
    python3-inquirer \
    python3-blessed \
    python3-readchar \
"

do_install() {
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check --platform manylinux_${MANYLINUX_VER}_${TARGET_ARCH} \
        -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-cache-dir --no-deps --trusted-host pypi.vizionsdk.com \
        pyvizionsdk==${PYVIZIONSDK_VER} -i https://pypi.vizionsdk.com/root/pyvizionsdk/+simple/

    PYVIZION_CTL_SCRIPT=$(find ${D}${PYTHON_SITEPACKAGES_DIR} -name pyvizion-ctl)
    if [ -f "$PYVIZION_CTL_SCRIPT" ]; then
        install -d ${D}${bindir}
        sed -i -e '1s@.*@#!/usr/bin/env python3@' "$PYVIZION_CTL_SCRIPT"
        sed -i 's|${TMPDIR}|/usr/bin|g' "$PYVIZION_CTL_SCRIPT"
        install -m 0755 "$PYVIZION_CTL_SCRIPT" ${D}${bindir}/
        rm -rf $(dirname "$PYVIZION_CTL_SCRIPT")
    else
        bbfatal "pyvizion-ctl script not found after pip install. Check the contents of the aarch64 wheel."
    fi
}

INSANE_SKIP:${PN} = "already-stripped file-rdeps buildpaths"

FILES:${PN} = "\
    ${bindir}/pyvizion-ctl \
    ${libdir}/python*/site-packages/pyvizionsdk \
    ${libdir}/python*/site-packages/pyvizionsdk-*.dist-info \
    ${libdir}/python*/site-packages/pyvizionsdk.libs \
"
