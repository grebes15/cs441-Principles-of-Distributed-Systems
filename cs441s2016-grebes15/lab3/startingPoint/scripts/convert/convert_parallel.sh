#! /bin/bash
pwd
echo ""
rm *thumb*
ls -alg *.jpg | wc -l
time ls *.jpg | parallel convert -geometry 120 {} thumb_{}
echo ""
ls -alg *.jpg | wc -l


