#!/bin/bash

set -e

echo -e "\n=========================================="
echo "1) Stopping old Docker containers"
echo "=========================================="

docker-compose down || echo "Warning: Docker down failed or no containers running"

echo -e "\n=========================================="
echo "2) Starting Docker containers"
echo "=========================================="
if docker-compose up -d; then
    echo "Docker containers are up and running."
else
    echo "Docker up failed!"
    exit 1
fi

echo -e "\n=========================================="
echo "Deployment Complete!"
echo "=========================================="


DB_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' postgres_db)

if [ -z "$DB_IP" ]; then
    echo "Could not find IP for postgres_db. Is the container running?"
else
    echo "HOST is running at: $DB_IP"
fi

read -p "Press enter to exit..."