package com.techsu.app;




import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.autoscaling.model.Ebs;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AccountAttribute;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeAccountAttributesRequest;
import com.amazonaws.services.ec2.model.DescribeAccountAttributesResult;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeDhcpOptionsRequest;
import com.amazonaws.services.ec2.model.DescribeDhcpOptionsResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeSpotDatafeedSubscriptionResult;
import com.amazonaws.services.ec2.model.DescribeSpotInstanceRequestsRequest;
import com.amazonaws.services.ec2.model.DescribeSpotInstanceRequestsResult;
import com.amazonaws.services.ec2.model.DescribeVolumesRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesResult;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.DhcpOptions;
import com.amazonaws.services.ec2.model.EbsBlockDevice;
import com.amazonaws.services.ec2.model.EbsInstanceBlockDevice;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStatus;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.Volume;
import com.amazonaws.services.ec2.model.Vpc;
import com.amazonaws.services.elastictranscoder.model.Permission;
import com.amazonaws.services.opsworks.model.DescribePermissionsRequest;
import com.amazonaws.services.opsworks.model.DescribePermissionsResult;
import com.amazonaws.services.opsworks.model.RdsDbInstance;
import com.amazonaws.services.rds.AmazonRDSClient;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;

public class Awsbasicapp{
	private static com.amazonaws.internal.ListWithAutoConstructFlag<RdsDbInstance> rdsDbInstances;
	private static String value;
	private static ArrayList<String> instanceIds;
	private static ArrayList<String> spotInstanceRequestIds;
	
	public static void main(String[] args) {
		String securitygroup=null;
		AWSCredentials credentials=new BasicAWSCredentials("accesskey","secretkey");
		AmazonEC2Client ec2=new AmazonEC2Client(credentials);
		//spotrequestInstances in ec2
		
		
		
		
		/*DHCP OPtions*/
		System.out.println("dhcp details");
		DhcpOptions dhcp;
		DescribeDhcpOptionsRequest dhcpreq=new DescribeDhcpOptionsRequest();
		DescribeDhcpOptionsResult dhcpresult=ec2.describeDhcpOptions();
		System.out.println("i am here");
		List<DhcpOptions> dhcplist=ec2.describeDhcpOptions().getDhcpOptions();
		System.out.println("list length:--"+dhcplist.size());
		for(int i=0;i<dhcplist.size();i++)
		{
			dhcp=dhcplist.get(i);
			System.out.println("dhcpoptions--->"+dhcp.getDhcpOptionsId());
			System.out.println(dhcp.getDhcpConfigurations().size());
		}

	
		DescribeVolumesRequest volreq=new DescribeVolumesRequest();
		DescribeVolumesResult volres=ec2.describeVolumes();
		//System.out.println(volres.getVolumes().size());
		Volume v;
		List<Volume> vollist=volres.getVolumes();
		//System.out.println(vollist);
		for(int i=0;i<vollist.size();i++)
		{
			v=vollist.get(i);
			System.out.println(v.getIops());
			System.out.println(v.getAvailabilityZone());
			System.out.println(v.getKmsKeyId());
			System.out.println(v.getSnapshotId());
			System.out.println(v.getVolumeId());
			System.out.println(v.getCreateTime());
			System.out.println(v.getEncrypted());
		}
		
		
		
		DescribeInstancesRequest describeInstancesRequest=new DescribeInstancesRequest();
		describeInstancesRequest.setRequestCredentials(credentials);
	//Descriing availabilty zones
		
		
//Acccount attributes		
		InstanceStatus status=new InstanceStatus();
		AccountAttribute accountAttribute=new AccountAttribute();
		DescribeAccountAttributesRequest describeAccountAttributesRequest=new DescribeAccountAttributesRequest();
		
		/*Filter filter=new Filter();
		System.out.println(filter.getName());
	*/
		
		DescribePermissionsRequest permissionsRequest=new DescribePermissionsRequest();
		DescribePermissionsResult permissionsResult=new DescribePermissionsResult();
		permissionsResult.getPermissions();
		
		System.out.println("determination");
		instanceIds=new ArrayList<String>();
		AmazonRDSClient rdsClient=new AmazonRDSClient(credentials);
		
		try {
			
			//AWS with RDS
			System.out.println("AWS WITH RDS");
				System.out.println("----------------");
			//DBInstance dbis=new DBInstance();
			DescribeDBInstancesRequest dbInsrequest=new DescribeDBInstancesRequest();
			DescribeDBInstancesResult dbInsresult=rdsClient.describeDBInstances(dbInsrequest);
			DBInstance dbis;
			List<DBInstance> list=dbInsresult.getDBInstances();
			//System.out.println(dbInsresult.getDBInstances());
			System.out.println("list length:--"+list.size());
			
			
			for(int i=0;i<list.size();i++)
			{
				dbis=list.get(i);
				System.out.println(dbis.getAllocatedStorage());
				System.out.println(dbis.getDBSubnetGroup());
			}
			
			System.out.println("#####################");
			//AWS with EC2
			System.out.println("-------------------------------------");
			System.out.println("AWS WITH EC2");
			System.out.println("-------------------------------");
			
			
			DescribeInstancesResult result=ec2.describeInstances(describeInstancesRequest);
			DescribeAccountAttributesResult accountrequest=ec2.describeAccountAttributes();
			
			/*RunInstancesRequest runInstancesRequest=new RunInstancesRequest();
			RunInstancesResult run_instances_result=ec2.runInstances(runInstancesRequest);
			System.out.println(run_instances_result.getReservation());
			*/	System.out.println("tttoooooooooootto");
					
			/*System.out.println(ec2.describeVpcs().getVpcs());*/
			
			
			
			System.out.println(ec2.describeVpcs());
			System.out.println(ec2.describeAvailabilityZones());
			/*System.out.println(ec2.);*/
			
			
			
				
				List<Reservation> describeresult=result.getReservations();
				List<Reservation> reservations=result.getReservations();
				System.out.println(reservations);
				List<Instance> instances;
				for(Reservation res:reservations)
				{
					instances=res.getInstances();
					for(Instance ins:instances)
					{
						
						
						//System.out.println(accountrequest);
						System.out.println("Account attributes 1:--"+accountrequest.getAccountAttributes().get(1));
						System.out.println("account attributes 2:--"+accountrequest.getAccountAttributes().get(2));
						System.out.println("account attributes 3:"+accountrequest.getAccountAttributes().get(3));
						System.out.println("accont attributes 4 :"+accountrequest.getAccountAttributes().get(4));
						System.out.println("accont attributes 4 :"+accountrequest.getAccountAttributes().get(5));
						
						String instanceid=ins.getInstanceId();
						System.out.println("instanceId:-->"+ins.getInstanceId());
						System.out.println("instanc trpe:-->"+ins.getInstanceType());
						System.out.println("imageid:-->"+ins.getImageId());
						System.out.println("dnsname:-->"+ins.getPrivateDnsName());
						System.out.println("private ip address:-->"+ins.getPrivateIpAddress());
						System.out.println("PublicDnsName:-->"+ins.getPublicDnsName());
						System.out.println("keyName:-->"+ins.getKeyName());
						System.out.println("security groups:-->"+ins.getSecurityGroups());
						System.out.println("platform:-->"+ins.withInstanceId(instanceid).getPlatform());
						System.out.println(ins.getRootDeviceName());
						System.out.println(ins.getArchitecture());
												//elastic ips details
						EbsBlockDevice ebs=new EbsBlockDevice();
						
						System.out.println(ec2.describeVolumes());
						
						
						
						System.out.println(instanceid);
						System.out.println("ip:-->"+ins.getPrivateIpAddress());
						System.out.println("InstanceId-->"+ins.getInstanceId());
						System.out.println("vpcid:-->"+ins.getVpcId());
						
						
						InstanceState state=ins.getState();
						System.out.println("status:-->"+state);
						
						InstanceStatus ins_status=new InstanceStatus();
						InstanceStatus a;
						a=ins_status.withInstanceId(ins.getInstanceId());
						System.out.println(a);
						System.out.println("###@@@@@###@@@@@###");
						System.out.println("Tags");
						List taglist=ins.getTags();
						Tag t;
						System.out.println("list of tags:-->"+taglist);
						for(int i=1;i<taglist.size();i++)
						{
							t=(Tag) taglist.get(i);
							System.out.println(t.getKey());
							System.out.println(t.getValue());
						}
						
						/*Availabilityzones*/
						System.out.println("*******");
						System.out.println("we are in zone");
						DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();
						System.out.println(availabilityZonesResult.getAvailabilityZones().size());
						int avail;
						avail=availabilityZonesResult.getAvailabilityZones().size();
						
						System.out.println(avail);
						
							List<AvailabilityZone> az=availabilityZonesResult.getAvailabilityZones();
							
							System.out.println(az.get(1).getRegionName());
							System.out.println(az.get(2).getState());
							System.out.println(az.get(3).getZoneName());
				
					}
					
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}