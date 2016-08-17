#! /bin/bash
pwd
echo ""
rm *thumb*
ls -alg *.jpg | wc -l
time for i in *.jpg ; do convert -geometry 120 "$i" "thumb_${i%.*}.jpg" ; done
echo ""
ls -alg *.jpg | wc -l
