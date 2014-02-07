#!/bin/bash

# error handling
function err_exit { echo -e 1>&2; exit 1; }

# check if proper arguments are supplied
if [ $# -ne 2 ]; then
  echo -e "\n Usage error!\n Example: $0 http://example.com report.csv\n"
  exit 1
fi

# check if url is valid
if [[ ! $1 =~ ^(https?|ftp)://(-\.)?([^\s/?\.#-]+\.?)+(/[^\s]*)?$ ]];then
    echo "URL not valid"
    err_exit
fi

# check if csv file is valid
if [[ ! $2 =~ ^(.)+\.csv$ ]]; then
    echo "File not valid\n Example: report.csv" 
    err_exit
fi

# remove log if exists
if [ -f $2 ]; then
   echo "Removing existing log.."
   rm $2 || err_exit
fi

# Create file
touch $2

#Get links from url
links=$(curl $1 2>&1 | grep -o -E '<a.*href="([-A-Za-z0-9\+&@/%?=~_|!:,.;]*[-A-Za-z0-9\+&@#/%=~_|])"' | grep -o -E 'href="([^"#]+)"' | cut -d'"' -f2)

broken_links=0
total_links=0

echo "" >> $2
echo "-- CSV Format --" >> $2
echo "Link, Broken" >> $2

for link in $links
do
    response=$(curl --write-out %{http_code} --silent --output /dev/null $link)

    total_links=$[total_links+1]

    # Check if link is broken
    if [ $response -eq 200 ]
    then
    	echo "$link, True" >> $2
    else
    	broken_links=$[broken_links+1]
   	echo "$link, False" >> $2
    fi
done

sed -i "1s/^/Broken Links: $broken_links\n/" $2
sed -i "1s/^/Total Links: $total_links\n/" $2

echo "Done - Report Generated"
