#!/bin/bash

# Pull latest code
git pull

# Stop existing containers
docker-compose down

# Rebuild and start
docker-compose up -d --build

# Show logs
docker-compose logs -f