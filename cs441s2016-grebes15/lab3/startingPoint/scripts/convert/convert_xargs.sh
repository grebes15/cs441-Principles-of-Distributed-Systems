#! /bin/bash
pwd
echo ""
rm *thumb*
ls -alg *.jpg | wc -l
time ls -1 *.jpg | xargs -n 1 bash -c 'convert -geometry 120 "$0" "thumb_${0%.*}.jpg"'
echo ""
ls -alg *.jpg | wc -l
