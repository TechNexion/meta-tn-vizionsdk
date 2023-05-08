ROOTFS_POSTPROCESS_COMMAND:append:mx93-nxp-bsp = "install_vizionviewer; "
ROOTFS_POSTPROCESS_COMMAND:append:mx8-nxp-bsp = "install_vizionviewer; "

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
