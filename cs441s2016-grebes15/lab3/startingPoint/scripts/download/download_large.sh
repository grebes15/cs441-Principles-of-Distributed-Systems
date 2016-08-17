#! /bin/bash
ls -alg ../../images/large/*.jpg | wc -l
for i in {1..500}
do
	wget http://loremflickr.com/320/240/paris
	cp large "large_$i.jpg"
	rm large
# time for i in *.jpg ; do convert -geometry 120 "$i" "thumb_${i%.*}.jpg" ; done
done
ls -alg ../../images/large/*.jpg | wc -l

