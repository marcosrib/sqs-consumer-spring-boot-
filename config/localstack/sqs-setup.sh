#!/usr/bin/env sh

apt-get install -y jq > /dev/null 2>&1

sns_topic_arn=`awslocal sns create-topic --name topic-consumer | jq -r '.TopicArn'`

sqs_queue_names=('consumer-queue')

for i in "${!sqs_queue_names[@]}"; do
 sns_subscriptions[i]=`awslocal sns subscribe --topic-arn $sns_topic_arn --protocol sqs \
   --notification-endpoint $(awslocal sqs create-queue --queue-name ${sqs_queue_names[i]} | jq -r '.QueueUrl')`;
done

awslocal sqs list-queues
awslocal sns list-subscriptions
