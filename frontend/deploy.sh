#!/usr/bin/env bash

XXXX="sec"
DROPLET_URL="marcusravnjensen.dk"

echo "##############################"
echo "Building the frontend project"
echo "##############################"
npm run build

echo "##############################"
echo "Deploying Frontend project..."
echo "##############################"

scp -r ./build root@$DROPLET_URL:/var/www/$XXXX

