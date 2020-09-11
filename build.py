import subprocess
import xml.etree.ElementTree as ET

NS = '{http://maven.apache.org/POM/4.0.0}'

pom_tree = ET.parse('pom.xml')
pom_root = pom_tree.getroot()
version = pom_root.find(NS + 'version').text
group_id = pom_root.find(NS + 'groupId').text
company = group_id.split('.')[-1]
artifact_id = pom_root.find(NS + 'artifactId').text

parent_pom = pom_root.find(NS+'parent')
parent_pom_version = parent_pom.find(NS + 'version').text

tag = company + '/' + artifact_id + ':' + version
parent_pom_tag = 'PARENT_POM_TAG=' + parent_pom_version

print("building {} using parent pom {}".format(tag, parent_pom_version))

build = subprocess.Popen(['docker', 'build', '-f', 'src/main/docker/Dockerfile', '-t', tag, '--build-arg', parent_pom_tag, '.'])
build.wait()
