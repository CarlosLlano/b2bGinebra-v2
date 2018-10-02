FROM jboss/wildfly:11.0.0.Final


#despligue del jar de postgres
ENV postgres_module_dir=/opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/
RUN mkdir -p ${postgres_module_dir}
ADD module.xml ${postgres_module_dir}
ADD postgresql-9.4-1201.jdbc41.jar ${postgres_module_dir}

#configuracion del datasource
ENV config_dir=/opt/jboss/wildfly/standalone/configuration
ADD standalone.xml ${config_dir}

#despliegue del archivo war
ADD /war/b2bGinebra.war /opt/jboss/wildfly/standalone/deployments/

#creacion del usuario administrador
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

#inicio del servidor
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

