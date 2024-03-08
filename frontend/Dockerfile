FROM node:18 AS build-step
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build --prod


FROM nginx:alpine
COPY default.conf /etc/nginx/conf.d/default.conf
COPY ./ssl/localhost.crt /etc/nginx/ssl/localhost.crt
COPY ./ssl/localhost.key /etc/nginx/ssl/localhost.key
COPY --from=build-step /app/dist/frontend /usr/share/nginx/html

EXPOSE 80 443
CMD ["nginx", "-g", "daemon off;"]

