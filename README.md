# Project Cronus  [![Build Status](https://travis-ci.org/rogrs/cronus.svg?branch=master)](https://travis-ci.org/rogrs/cronus) [![Dependency Status](https://david-dm.org/rogrs/cronus.svg)](https://david-dm.org/rogrs/cronus) [![devDependency Status](https://david-dm.org/rogrs/cronus/dev-status.svg)](https://david-dm.org/rogrs/cronus#info=devDependencies) [![peerDependency Status](https://david-dm.org/rogrs/cronus/peer-status.svg)](https://david-dm.org/rogrs/cronus#info=peerDependencies) [![peerDependency Status](https://david-dm.org/rogrs/cronus/peer-status.svg)](https://david-dm.org/rogrs/cronus#info=peerDependencies) [![codecov](https://codecov.io/gh/rogrs/cronus/branch/master/graph/badge.svg)](https://codecov.io/gh/rogrs/cronus)

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.
2. [Yarn][]: We use Yarn to manage Node dependencies.
   Depending on your system, you can install Yarn either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    yarn install

We use yarn scripts and [Webpack][] as our build system.


Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    yarn start

[Yarn][] is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `yarn update` and `yarn install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `yarn help update`.

The `yarn run` command will list all of the scripts available to run for this project.
