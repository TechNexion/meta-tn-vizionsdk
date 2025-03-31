SUMMARY = "TechNexion PyVizionSDK"
LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial_tn"

inherit python3native

MANYLINUX_VER = "2_37"
PYVIZIONSDK_VER = "25.4.1"

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
"

do_install() {
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check \
        -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-cache-dir --no-deps inquirer
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check \
        -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-cache-dir --no-deps blessed
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check \
        -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-cache-dir --no-deps readchar
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check --platform manylinux_${MANYLINUX_VER}_${TARGET_ARCH} \
        -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-cache-dir --no-deps \
        pyvizionsdk==${PYVIZIONSDK_VER} -i https://pypi.vizionsdk.com/root/pyvizionsdk/+simple/

    sed -i -e '1s@#!/bin/sh@#!/usr/bin/python3@' -e "/'''/,/'''/d" ${WORKDIR}/image/usr/lib/python*/site-packages/bin/pyvizion-ctl
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/image/usr/lib/python*/site-packages/bin/pyvizion-ctl ${D}${bindir}
    rm -rf ${WORKDIR}/image/usr/lib/python*/site-packages/bin
}

INSANE_SKIP:${PN} = "already-stripped file-rdeps"

FILES:${PN} = "\
    ${bindir}/pyvizion-ctl \
    ${libdir}/python*/site-packages/pyvizionsdk \
    ${libdir}/python*/site-packages/pyvizionsdk-*.dist-info \
    ${libdir}/python*/site-packages/pyvizionsdk.libs \
    ${libdir}/python*/site-packages/inquirer \
    ${libdir}/python*/site-packages/inquirer-*.dist-info \
    ${libdir}/python*/site-packages/blessed \
    ${libdir}/python*/site-packages/blessed-*.dist-info \
    ${libdir}/python*/site-packages/readchar \
    ${libdir}/python*/site-packages/readchar-*.dist-info \
"
