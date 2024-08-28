ROOTFS_POSTPROCESS_COMMAND:append = " install_vizionviewer; modify_weston; "

install_vizionviewer() {
    #bbplain "---->>> Add weston launcher of VizionViewer"
    _weston_ini="${IMAGE_ROOTFS}${sysconfdir}/xdg/weston/weston.ini"
    if ! grep -q "icon=/opt/vizionviewer/icons/" ${_weston_ini}
    then
        printf "[launcher]\nicon=/usr/share/weston/terminal.png\npath=/usr/bin/weston-terminal\n" >> ${_weston_ini}
        printf "[launcher]\nicon=/opt/vizionviewer/icons/icon_24x24.png\npath=/usr/bin/vizionviewer" >> ${_weston_ini}
    fi
}

modify_weston() {
    #bbplain "---->>> modify weston launcher to wait vizionpanel wake up"
    _weston_service="${IMAGE_ROOTFS}${systemd_unitdir}/system/weston.service"
    sed -i 's|Before=graphical.target|After=graphical.target|g' ${_weston_service}
}
