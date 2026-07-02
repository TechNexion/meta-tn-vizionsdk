VIZIONSDK_IMAGE_PACKAGES = "vizionsdk vizionviewer pyvizionsdk tn-apt-list"

IMAGE_INSTALL:remove:tn-vizionsdk = "packagegroup-tn-vizionsdk"
CORE_IMAGE_EXTRA_INSTALL:remove:tn-vizionsdk = "packagegroup-tn-vizionsdk"
IMAGE_INSTALL:append:tn-vizionsdk = " ${VIZIONSDK_IMAGE_PACKAGES}"

ROOTFS_POSTPROCESS_COMMAND:append:tn-vizionsdk:mx9-generic-bsp = "install_vizionviewer; "
ROOTFS_POSTPROCESS_COMMAND:append:tn-vizionsdk:mx8-generic-bsp = "install_vizionviewer; "
ROOTFS_POSTPROCESS_COMMAND:remove:mx91-generic-bsp = "install_vizionviewer;"

install_vizionviewer() {
	#bbplain "---->>> Add weston launcher of VizionViewer"
	_weston_ini="${IMAGE_ROOTFS}${sysconfdir}/xdg/weston/weston.ini"
	if ! grep -q "icon=/opt/vizionviewer/icons/" ${_weston_ini}
		sed -i 's|icon=*|icon=/opt/vizionviewer/icons/icon_24x24.png|g' ${_weston_ini}
		sed -i 's|path=*|path=/usr/bin/vizionviewer|g' ${_weston_ini}
	then
		printf "[launcher]\nicon=/opt/vizionviewer/icons/icon_24x24.png\npath=/usr/bin/vizionviewer" >> ${_weston_ini}
	fi
}
