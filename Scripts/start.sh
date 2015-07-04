#!/bin/bash
echo "0104392550" | sudo su <<EOF
echo 3 > /sys/class/backlight/acpi_video0/brightness
sudo /opt/lampp/lampp start
exit
echo "Done :)"

EOF
