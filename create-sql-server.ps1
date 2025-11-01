$RG = "rg-devforge-yardflow"
$LOCATION = "brazilsouth"
$AZURE_SQL_SERVER="sqlserver-yardflow-rm554556"
$AZURE_SQL_DATABASE="devforge-yardflowdb"
$AZURE_SQL_USERNAME="admsql"
$AZURE_SQL_PASSWORD="Fiap@2tdsvms"


az group create --name $RG --location $LOCATION
az sql server create -l $LOCATION -g $RG -n $AZURE_SQL_SERVER -u $AZURE_SQL_USERNAME -p $AZURE_SQL_PASSWORD --enable-public-network true
az sql db create -g $RG -s $AZURE_SQL_SERVER -n $AZURE_SQL_DATABASE --service-objective Basic --backup-storage-redundancy Local --zone-redundant false
az sql server firewall-rule create -g $RG -s $AZURE_SQL_SERVER -n AllowAll --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255


