import random
import uuid

import requests


companyNames = ["Smart Cab", "Boring Taxis", "Fast Taxis", "Red Cabs"]

for i in range(0, 10000):
    
    res = requests.post('http://localhost:9090/tripstart', json={
    "tripID":str(uuid.uuid4()),
    "taxiID":"fdfdf",
    "companyName":random.choice(companyNames),
    "pickupCensusTract":"1",
    "pickupCommunityAreaCode":"999",
    "pickupCentroidLatitude":"191.2",
    "pickupCentroidLongitude":"-87.98",
    "pickupCentroidLocation":"xloc",
    "communityAreaCode":"363"
    })
    print("Sending {}".format(i))
#     print(res.json())