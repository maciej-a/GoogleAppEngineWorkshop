#!/bin/bash
echo removing generated files - uncomment script lines!
find . -name .metadata | while read line; do rm -rfv $line; done
find . -name RemoteSystemsTempFiles | while read line; do rm -rfv $line; done
find . -name .classpath | while read line; do rm -v $line; done
find . -name .project | while read line; do rm -v $line; done
find . -name target | while read line; do rm -rfv $line; done
find . -name .settings | while read line; do rm -rfv $line; done

rm -v ~/.appcfg_oauth2_tokens_java
rm ~/.bash_history
sudo rm /root/.bash_history
echo "Cleaning up firefox"
rm -rf ~/.mozilla/firefox/rrwkccld.default/*

