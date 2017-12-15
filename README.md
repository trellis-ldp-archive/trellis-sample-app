# Sample Trellis Application

[![Build Status](https://travis-ci.org/trellis-ldp/trellis-sample-app.png?branch=master)](https://travis-ci.org/trellis-ldp/trellis-sample-app)

This project provides a skeleton for a custom Trellis application. For a full, working
example, please examine the trellis-rosid-app directory in
[trellis-ldp/trellis-rosid](https://github.com/trellis-ldp/trellis-rosid).

You will need to add a persistence layer to make this code work. To do that, add your
code to the `TrellisApplication` class inside the `run` method. It will be obvious.

From there, you may wish to make further modifications to that method or to the
`TrellisConfiguration` class.

## Running Trellis

Unpack a zip or tar distribution. In that directory, modify `./etc/config.yml` to match the
desired values for your system.

To run trellis directly from within a console, issue this command:

```bash
$ ./bin/trellis-app server ./etc/config.yml
```

## Installation

To install Trellis as a [`systemd`](https://en.wikipedia.org/wiki/Systemd) service on linux,
follow the steps below. `systemd` is used by linux distributions such as CentOS/RHEL 7+ and Ubuntu 15+.

1. Move the unpacked Trellis directory to a location such as `/opt/trellis`.
   If you choose a different location, please update the `./etc/trellis.service` script.

2. Edit the `./etc/environment` file as desired (optional).

3. Edit the `./etc/config.yml` file as desired (optional).

4. Create a trellis user:

```bash
$ sudo useradd -r trellis -s /sbin/nologin
```

5. Create data directories. A different location can be used, but then please update
   the `./etc/config.yml` file.

```bash
$ sudo mkdir /var/lib/trellis
$ sudo chown trellis.trellis /var/lib/trellis
```

6. Install the systemd file:

```bash
$ sudo ln -s /opt/trellis/etc/trellis.service /etc/systemd/system/trellis.service
```

7. Reload systemd to see the changes

```bash
$ sudo systemctl daemon-reload
```

8. Start the trellis service

```bash
$ sudo systemctl start trellis
```

To check that trellis is running, check the URL: `http://localhost:8080`

Application health checks are available at `http://localhost:8081/healthcheck`

## Building Trellis

1. Run `./gradlew clean install` to build the application or download one of the releases.
2. Unpack the appropriate distribution in `./build/distributions`
3. Start the application according to the steps above

## Configuration

The web application wrapper (Dropwizard.io) makes many [configuration options](http://www.dropwizard.io/1.2.0/docs/manual/configuration.html)
available. Any of the configuration options defined by Dropwizard can be part of your application's configuration file.

