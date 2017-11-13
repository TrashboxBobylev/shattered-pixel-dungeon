#!/bin/sh

echo "Generated APK"
pwd; ls -l ./app/build/outputs/apk

# Checkout autobuild branch
cd ..
git clone https://github.com/TrashboxBobylev/torment-pixel-dungeon.git --branch autobuild --single-branch tormentPD_autobuild
cd tormentPD_autobuild

# Copy newly created APK into the target directory
mv ../torment-pixel-dungeon/app/build/outputs/apk/tormentPD.apk ./autobuild
echo "Target APK"
pwd; ls -l ./autobuild

# Setup git for commit and push
git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"
git remote add origin-master https://${AUTH_KEY}@github.com/TrashboxBobylev/torment-pixel-dungeon > /dev/null 2>&1
git add ./autobuild/tormentPD.apk

# We don’t want to run a build for a this commit in order to avoid circular builds: 
# add [ci skip] to the git commit message
git commit --message "Snapshot autobuild N.$TRAVIS_BUILD_NUMBER [ci skip]"
git push origin-master