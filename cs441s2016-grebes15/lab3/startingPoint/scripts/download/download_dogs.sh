#! /bin/bash
ls -alg ../../images/dogs/*.jpg | wc -l
for i in {1..20}
do
  wget http://loremflickr.com/320/240/dog
  cp dog "dog_$i.jpg"
  rm dog
# time for i in *.jpg ; do convert -geometry 120 "$i" "thumb_${i%.*}.jpg" ; done
done
ls -alg ../../images/dogs/*.jpg | wc -l

