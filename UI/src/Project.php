<?php
namespace mssdev;
use Ratchet\MessageComponentInterface;
use Ratchet\ConnectionInterface;
use Exception;

class Project implements MessageComponentInterface{
    protected $clients;
    private $connectedUsers;
    public function __construct(){
        $this->clients = new \SplObjectStorage;
        $this->connectedUsers = [];
    }
    public function onOpen(ConnectionInterface $conn)
    {
        $this->clients->attach($conn);
        $this->connectedUsers[$conn->resourceId] = $conn;
        echo "{$conn->resourceId} connected\n";
    }
    public function onMessage(ConnectionInterface $from, $msg){
        $msg = json_decode($msg);
        if($msg->type = "new-client"){
            //Client introduction
            $from->uid = $msg->uid;
        }
    }
    public function onClose(ConnectionInterface $conn){
        $this->clients->detach($conn);
        echo "{$conn->resourceId} disconnected\n";
    }
    public function onError(ConnectionInterface $conn, \Exception $e){
        echo "Error occurred :[ ERROR MESSAGE: {$e->getMessage()}\n";
    }
    private function sendMessage($message){
        foreach($this->connectedUsers as $user){
            $this->send(json_encode($message));
        }
    }
    private function getArtifacts(){
        try{
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, "https://localhost/get-artifacts");
            curl_setopt($ch, CURLOPT_PORT, 16802);
            curl_setopt($ch, CURLOPT_POST, 1);
            //Give dummy payload to fool the server into not dropping the packet
            $payload = new \stdClass();
            $payload->content = "joel is neat";
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($payload));
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_TIMEOUT, 100);
            curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            $artifacts = curl_exec($ch);
            curl_close($ch);
            return json_decode($artifacts);
        } catch (Exception $e) {
            echo $e->getMessage();
        }
    }
}