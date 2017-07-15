'''
0    ID
1    TripID
2    TaxiID
3    TripSeconds
4    TripMiles
5    PickupCensusTract
6    DropoffCensusTract
7    PickupCommunityArea
8    DropoffCommunityArea
9    Fare
10    Tips
11    Tolls
12    Extras
13    TripTotal
14    PaymentType
15    Company
16    PickupCentroidLatitude
17    PickupCentroidLongitude
18    DropoffCentroidLatitude
19    DropoffCentroidLongitude
20    CommunityAreas

https://pythonprogramming.net/reading-csv-files-python-3/
'''
import argparse
import csv
from datetime import datetime, timedelta
import random
from time import sleep
import uuid

import requests

START_TIME_OFFSET = 3600
HTTP_ENDPOINT = ""
DATA_FILE = ""
DELAY = 0

def send_tripend_events():
    print("Using HTTP Endpoint: {}".format(HTTP_ENDPOINT))
    print("Using Data File: {}".format(DATA_FILE))
    print("Seconds Delay: {}".format(DELAY))
    
    with open(DATA_FILE, 'r') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=',')
        next(readCSV)  # skip the header row
        for row in readCSV:
    #      print(row)
    #      print(row[0])
    #      print(row[0], row[1], row[2],)
            sleep(DELAY)  # Time in seconds.
#             print(START_TIME_OFFSET)
#             print(row[3])
            secs = START_TIME_OFFSET + int(row[3])
            res = requests.post(HTTP_ENDPOINT, json={
            "tripID":row[1],
            "taxiID":row[2],
            "tripSeconds":row[3],
            "tripEndTime": (datetime.now() - timedelta(seconds=secs)).isoformat(),
            "companyName":row[15],
            "dropoffCensusTract":row[6],
            "fare":row[9],
            "tips":row[10],
            "tolls":row[11],
            "extras":row[12],
            "tripTotal":row[13],
            "paymentType":row[14],
            "dropoffCommunityArea":row[8],
            "dropoffCentroidLatitude":row[18],
            "dropoffCentroidLongitude":row[19],
            "dropoffCentroidLocation":"???"
            })
            print("Sending {}".format(row[0]))

#     print(res.json())
if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--endpoint', default='http://localhost:9090/tripend')
    parser.add_argument('--datafile', default='..\\test-data-v2-aa.csv')
    parser.add_argument('--delay', type=int, default=0)
    
    args = parser.parse_args()
    print(args)

    HTTP_ENDPOINT = args.endpoint
    DATA_FILE = args.datafile
    DELAY = args.delay
    
    send_tripend_events()
