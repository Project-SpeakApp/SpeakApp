FROM node:18 AS build-step
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build --prod


FROM nginx:alpine
COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=build-step /app/dist/frontend /usr/share/nginx/html

EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]

