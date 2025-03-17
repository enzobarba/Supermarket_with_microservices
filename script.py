import os
import subprocess

def build_microservices(base_path):
    try:
        print("Pulizia delle immagini Docker...")
        if os.name == "nt":
            subprocess.run(["powershell", "-Command", "docker images -q | ForEach-Object {docker rmi -f $_}"] , shell=True, check=False, cwd=base_path)
        else:
            subprocess.run("docker rmi -f $(docker images -q)", shell=True, check=False, cwd=base_path)
        
        for folder in os.listdir(base_path):
            if folder.lower() == "client":  
                print(f"Saltando la directory: {folder}")
                continue
            folder_path = os.path.join(base_path, folder)
            pom_path = os.path.join(folder_path, "pom.xml")
            
            if os.path.isdir(folder_path) and os.path.exists(pom_path):
                print(f"Trovato microservizio: {folder}")
                
                mvnw_cmd = "mvnw.cmd" if os.name == "nt" else "./mvnw"
                
                print(f"Eseguendo: {mvnw_cmd} install in {folder}")
                subprocess.run([mvnw_cmd, "install"], cwd=folder_path, shell=True, check=True)
                
                if "apigateway" in folder.lower():
                    image_name = folder.lower()
                else:
                    image_name = folder.lower().replace("service", "").strip("-") + "-service"
                
                print(f"Eseguendo: docker build -t {image_name}:latest . in {folder}")
                subprocess.run(["docker", "build", "-t", f"{image_name}:latest", "."], cwd=folder_path, shell=True, check=True)
                
                print(f"Costruzione completata per {folder}\n")
    except Exception as e:
        print(f"Errore: {e}")

if __name__ == "__main__":
    base_directory = r"C:\Users\enzo1\Desktop\Università\Magistrale\1° anno\1° semestre\Ingegneria dei Sistemi Distribuiti\Progetto Supermarket"
    build_microservices(base_directory)