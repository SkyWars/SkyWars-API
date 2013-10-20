#!/bin/sh
if [[ "${TRAVIS_SECURE_ENV_VARS}" == "true" ]]; then
    export MVN_SETTINGS_XML='<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd"><servers><server><id>repo-daboross-net</id><username>'"${REPO_USERNAME}"'</username><password>'"${REPO_PASSWORD}"'</password></server></servers></settings>'
    export KNOWN_HOST_SSH='|1|wPcvQE9BDavFe6leYQATAMurskE=|LZirQZaAuhFUCCw+J7Q/pedeTho= ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCzp6eC7j6+XncNqkPwH+s+lYrf01fOjFLhgctRtkAvJsdBui8Q4ys3fMG/6wvQovdjrqVoLL61J6feXhEZqPdJMETsOkbG6XTOSOb1HCZ1rK7rC++RRhgrINDQWl/aXrmcHkM6iIXsffmrAX8iswzNJjcZDCKKri04hYFQ84upWg+icUcAJqSJt9/LF+zvR1Cq64c5EFY1WOYScC83qhfNg1unZU1XVRiFir9+9rDdW5u0MPhE4hm9IWXD2/cbIFdUHQUXyTSS6GPmEyBhE7fuVa7Zw7g17jUNSqFgrRd29TmT793Mul90nPCGQqiXnFfRj7dvOYJpf1PmTZLiRICT'
    mkdir -p "${HOME}/.m2/"
    echo "${MVN_SETTINGS_XML}" > "${HOME}/.m2/settings.xml"
    mkdir -p "${HOME}/.ssh"
    chmod 700 "${HOME}/.ssh"
    echo "${KNOWN_HOST_SSH}" >> "${HOME}/.ssh/known_hosts"
else
    echo "No secure env vars, skipping setup."
fi
